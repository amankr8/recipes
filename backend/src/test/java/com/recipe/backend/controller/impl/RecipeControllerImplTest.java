package com.recipe.backend.controller.impl;

import com.recipe.backend.entity.Recipe;
import com.recipe.backend.exception.ResourceNotFoundException;
import com.recipe.backend.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RecipeControllerImpl.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class RecipeControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecipeService recipeService;

    @Test
    void getAllRecipes_shouldReturnListOfRecipes() throws Exception {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Pizza");
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Burger");
        when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(recipe1, recipe2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Pizza"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Burger"));
    }

    @Test
    void getRecipeById_shouldReturnRecipe_whenExists() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pizza");
        when(recipeService.getRecipeById(1L)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pizza"));
    }

    @Test
    void getRecipeById_shouldReturn404_whenNotFound() throws Exception {
        when(recipeService.getRecipeById(anyLong())).thenThrow(new ResourceNotFoundException("Recipe not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void searchRecipesByQuery_shouldReturnMatchingRecipes() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pizza");
        when(recipeService.searchRecipesByQuery("pizza")).thenReturn(Collections.singletonList(recipe));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/search").param("query", "pizza"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Pizza"));
    }
}

