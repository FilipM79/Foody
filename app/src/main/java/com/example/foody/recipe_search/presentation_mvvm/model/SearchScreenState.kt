package com.example.foody.recipe_search.presentation_mvvm.model

import com.example.foody.shared.domain.model.RecipeInfo

// 10-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// What do we do here?
data class SearchScreenState(var searchTerm: String, val recipeSearchState: RecipeSearchState) {
    companion object {
        val initialValue = SearchScreenState("", RecipeSearchState.Idle)
    }
}

sealed class RecipeSearchState {
    data object Idle : RecipeSearchState()
    data object Empty : RecipeSearchState()
    data object Loading: RecipeSearchState()
    data class Success(val mealList: List<RecipeInfo>): RecipeSearchState()
    data class Error(val message: String) : RecipeSearchState()
}