package com.example.foody.recipe_details.domain

import com.example.foody.domain.model.RecipeInfo

interface RecipeDetailsRepository {
    suspend fun getRecipeDetails(recipeId: String) : RecipeInfo

}