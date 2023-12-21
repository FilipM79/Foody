package com.example.foody.search.presentation_mvvm.model

import com.example.foody.domain.model.RecipeInfo

data class SearchScreenState(
    val recipes: List<RecipeInfo>
) {

    companion object {
        val initialValue = SearchScreenState(emptyList())
    }
}