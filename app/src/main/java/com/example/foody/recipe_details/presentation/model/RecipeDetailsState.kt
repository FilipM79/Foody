package com.example.foody.recipe_details.presentation.model

import com.example.foody.shared.domain.model.RecipeInfo

data class RecipeDetailsState(val detailsState: RecipeInfoState) {
    companion object {
        val initialValue = RecipeDetailsState(RecipeInfoState.RecipeInfoLoading)
    }
}

sealed class RecipeInfoState {
    data object RecipeInfoLoading: RecipeInfoState()
    data class RecipeInfoValue(val recipeDetails: RecipeInfo): RecipeInfoState()
    data class RecipeInfoError(val message: String) : RecipeInfoState()
}

