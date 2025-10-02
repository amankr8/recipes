package com.recipe.backend.service;

import com.recipe.backend.entity.Recipe;

import java.util.List;

public interface RecipeService {

    void loadRecipes();

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);

    List<Recipe> searchRecipesByQuery(String query);
}
