package com.recipe.backend.service.impl;

import com.recipe.backend.entity.Recipe;
import com.recipe.backend.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe createRecipe(Long id, String name) {
        Recipe r = new Recipe();
        r.setId(id);
        r.setName(name);
        r.setCookTimeMinutes(30L);
        r.setTags(List.of("Easy", "Test"));
        r.setCuisine("Italian");
        return r;
    }

    @Test
    void testGetAllRecipes() {
        List<Recipe> mockRecipes = List.of(createRecipe(1L, "Pizza"), createRecipe(2L, "Pasta"));
        when(recipeRepository.findAll()).thenReturn(mockRecipes);

        List<Recipe> result = recipeService.getAllRecipes();

        assertEquals(2, result.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testGetRecipeByIdFound() {
        Recipe recipe = createRecipe(1L, "Pizza");
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.getRecipeById(1L);

        assertEquals("Pizza", result.getName());
    }

    @Test
    void testGetRecipeByIdNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> recipeService.getRecipeById(1L));
    }
}

