package com.example.foody.recipe_details.presentation.model

import com.example.foody.shared.domain.model.RecipeInfo

data class RecipeDetailsState(val detailsState: RecipeInfoState) {
    companion object {
        val initialValue = RecipeDetailsState(RecipeInfoState.Loading)
    }
}

sealed class RecipeInfoState {
    data object Loading: RecipeInfoState()
    data class Value(val recipeDetails: RecipeInfo): RecipeInfoState()
    data class Error(val message: String) : RecipeInfoState()
}

