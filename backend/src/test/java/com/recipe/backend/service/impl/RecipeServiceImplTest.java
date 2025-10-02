package com.recipe.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.backend.entity.Recipe;
import com.recipe.backend.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @Mock private RecipeRepository recipeRepository;
    @Mock private RestTemplate restTemplate;

    @InjectMocks private RecipeServiceImpl recipeService;

    private Recipe createRecipe(String name) {
        Recipe r = new Recipe();
        r.setName(name);
        r.setCookTimeMinutes(30L);
        r.setTags(List.of("Easy", "Test"));
        r.setCuisine("Italian");
        return r;
    }

    @Test
    void testGetAllRecipes() {
        List<Recipe> mockRecipes = List.of(createRecipe("Pizza"), createRecipe("Pasta"));
        when(recipeRepository.findAll()).thenReturn(mockRecipes);

        List<Recipe> result = recipeService.getAllRecipes();

        assertEquals(2, result.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testGetRecipeByIdFound() {
        Recipe recipe = createRecipe("Pizza");
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.getRecipeById(1L);

        assertEquals("Pizza", result.getName());
    }

    @Test
    void testGetRecipeByIdNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> recipeService.getRecipeById(1L));
    }

    @Test
    void testLoadRecipes() throws Exception {
        String json = """
            {
              "recipes": [
                { "name": "Pizza", "cookTimeMinutes": 30, "tags": ["Italian"], "cuisine": "Italian" }
              ]
            }
        """;

        JsonNode node = new ObjectMapper().readTree(json);
        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(ResponseEntity.ok(node));

        recipeService.loadRecipes();

        verify(recipeRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testInitLoadsWhenEmpty() {
        when(recipeRepository.count()).thenReturn(0L);

        assertDoesNotThrow(() -> recipeService.init());

        verify(recipeRepository, times(1)).count();
    }
}

