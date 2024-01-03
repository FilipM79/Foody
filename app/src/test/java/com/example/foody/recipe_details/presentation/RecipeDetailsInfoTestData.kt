package com.example.foody.recipe_details.presentation

import com.example.foody.shared.domain.model.Ingredient
import com.example.foody.shared.domain.model.RecipeInfo

fun getRecipeInfoTestData(recipeId: String) = RecipeInfo(
    id  = recipeId,
    title = "Recipe Tile",
    cuisine = "Recipe Cuisine",
    category = "Recipe Category",
    ingredients = listOf(
        Ingredient(title = "Ingredient 1", measure = "1 cup")
    ),
    recipe = "Recipe Details",
    imageUrl = null,
    videoUrl = null,
    tags = listOf("First")
)
