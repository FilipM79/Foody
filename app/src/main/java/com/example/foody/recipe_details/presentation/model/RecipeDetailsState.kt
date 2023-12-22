package com.example.foody.recipe_details.presentation.model

import com.example.foody.shared.domain.model.RecipeInfo

data class RecipeDetailsState(
    val recipeDetails: RecipeInfo
) {

    companion object {
        val initialValue = RecipeDetailsState(RecipeInfo())
    }
}