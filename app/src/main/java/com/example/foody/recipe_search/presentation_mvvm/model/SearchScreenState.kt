package com.example.foody.recipe_search.presentation_mvvm.model

import com.example.foody.shared.domain.model.RecipeInfo

// 10-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

data class SearchScreenState(val searchBarState: SearchBarState, val recipeListState: RecipeListState) {
    companion object {
        val initialValue = SearchScreenState(SearchBarState.initial, RecipeListState.Idle)
    }
    
    fun clone(
        searchTerm: String = searchBarState.searchTerm,
        searchBarExpandedState: Boolean = searchBarState.expandedState,
        recipeListState: RecipeListState = this.recipeListState
    ) = copy(
        searchBarState = searchBarState.copy(
            searchTerm = searchTerm,
            expandedState = searchBarExpandedState
        ),
        recipeListState = recipeListState
    )
}

data class SearchBarState(val searchTerm: String, val expandedState: Boolean) {
    companion object {
        val initial = SearchBarState("", true)
    }
}

sealed class RecipeListState {
    data object Idle : RecipeListState()
    data object Empty : RecipeListState()
    data object Loading: RecipeListState()
    data class Success(val recipeList: List<RecipeInfo>): RecipeListState()
    data class Error(val message: String) : RecipeListState()
}