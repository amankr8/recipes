package com.recipe.backend.service;

import com.recipe.backend.entity.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);

    List<Recipe> searchRecipesByQuery(String query);
}
