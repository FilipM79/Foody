package com.example.foody.recipe_details.domain

import com.example.foody.shared.domain.model.RecipeInfo

interface RecipeDetailsSearchRepository {
    suspend fun getRecipeDetails(recipeId: String) : RecipeInfo

//    suspend fun getIngredientImage(ingredientTitle: String)
}