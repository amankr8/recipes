package com.recipe.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(("/api/recipes"))
@Validated
@Tag(name = "Recipe APIs", description = "Endpoints for fetching recipes")
public interface RecipeController {

    @GetMapping
    @Operation(summary = "Get all recipes", description = "Fetch a list of all recipes")
    ResponseEntity<?> getAllRecipes();

    @GetMapping("/{id}")
    @Operation(summary = "Get recipe by ID", description = "Fetch a single recipe by its ID")
    ResponseEntity<?> getRecipeById(@PathVariable Long id);

    @GetMapping("/search")
    @Operation(summary = "Search recipes by query", description = "Search for recipes based on a query string")
    ResponseEntity<?> searchRecipesByQuery(
            @RequestParam
            @NotBlank(message = "Query parameter cannot be blank")
            @Size(min = 3, max = 50, message = "Query must be between 3 and 50 characters")
            String query
    );
}
