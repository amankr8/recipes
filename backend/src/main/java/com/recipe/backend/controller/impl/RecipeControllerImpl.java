package com.recipe.backend.controller.impl;

import com.recipe.backend.controller.RecipeController;
import com.recipe.backend.entity.Recipe;
import com.recipe.backend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecipeControllerImpl implements RecipeController {

    private final RecipeService recipeService;

    @Override
    public ResponseEntity<?> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok().body(recipes);
    }

    @Override
    public ResponseEntity<?> getRecipeById(Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok().body(recipe);
    }

    @Override
    public ResponseEntity<?> searchRecipesByQuery(String query) {
        List<Recipe> recipes = recipeService.searchRecipesByQuery(query);
        return ResponseEntity.ok().body(recipes);
    }
}
