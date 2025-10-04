package com.recipe.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.recipe.backend.entity.Recipe;
import com.recipe.backend.exception.ResourceNotFoundException;
import com.recipe.backend.repository.RecipeRepository;
import com.recipe.backend.service.RecipeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RestTemplate restTemplate;

    @PersistenceContext
    private final EntityManager entityManager;

    @Value("${dummyApi.recipes.url}")
    private String recipesApiUrl;

    @PostConstruct
    public void init() {
        try {
            if (recipeRepository.count() == 0) {
                loadRecipes();
                reindex();
            }
        } catch (Exception e) {
            log.warn("Failed to load users on startup", e);
        }
    }

    private void reindex() {
        Search.mapping(entityManager.getEntityManagerFactory())
                .scope(Recipe.class)
                .massIndexer()
                .start();
    }

    private void loadRecipes() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
                recipesApiUrl, JsonNode.class);
        JsonNode recipesNode = Objects.requireNonNull(response.getBody()).get("recipes");

        List<Recipe> recipes = new ArrayList<>();
        for (JsonNode recipeNode : recipesNode) {
            Recipe recipe = new Recipe();
            recipe.setName(recipeNode.get("name").asText());
            recipe.setCookTimeMinutes(recipeNode.get("cookTimeMinutes").asLong());
            List<String> tags = new ArrayList<>();
            for (JsonNode tagNode : recipeNode.withArray("tags")) {
                tags.add(tagNode.asText());
            }
            recipe.setTags(tags);
            recipe.setCuisine(recipeNode.get("cuisine").asText());

            recipes.add(recipe);
        }
        recipeRepository.saveAll(recipes);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Recipe not found with id: " + id));
    }

    @Override
    public List<Recipe> searchRecipesByQuery(String query) {
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.search(Recipe.class)
                .where(f -> f.simpleQueryString()
                        .fields("name", "cuisine", "tags")
                        .matching(query + "*"))
                .fetchHits(20);
    }
}
