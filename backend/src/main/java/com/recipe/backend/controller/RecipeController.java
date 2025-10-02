package com.recipe.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(("/api/recipes"))
public interface RecipeController {

    @GetMapping
    ResponseEntity<?> getAllRecipes();

    @GetMapping("/search")
    ResponseEntity<?> searchRecipesByQuery(@RequestParam String query);

    @GetMapping("/{id}")
    ResponseEntity<?> getRecipeById(@PathVariable Long id);
}
