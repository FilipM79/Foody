package com.example.foody.search.presentation_mvvm.model

import com.example.foody.shared.domain.model.RecipeInfo

// 10-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// What do we do here?
data class SearchScreenState(val searchTerm: String, val recipes: List<RecipeInfo>) {
    companion object {
        val initialValue = SearchScreenState("", emptyList())
    }
}