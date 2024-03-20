package com.example.foody.recipe_search.presentation_mvvm.model

import com.example.foody.shared.domain.model.RecipeInfo

// 10-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

data class SearchScreenState(
    val searchBarState: SearchBarState,
    val recipeSearchState: RecipeSearchState,
    val randomRecipe: RecipeInfo
) {
    companion object {
        val initialValue = SearchScreenState(
            searchBarState = SearchBarState.initial,
            recipeSearchState = RecipeSearchState.Idle,
            randomRecipe = RecipeSearchState.Random(RecipeInfo.initial).randomRecipe
        )
    }
    
    fun clone(
        searchTerm: String = searchBarState.searchTerm,
        searchBarExpandedState: Boolean = searchBarState.expandedState,
        recipeSearchState: RecipeSearchState = this.recipeSearchState,
        randomRecipe: RecipeInfo = this.randomRecipe
    ) = copy(
        searchBarState = searchBarState.copy(
            searchTerm = searchTerm,
            expandedState = searchBarExpandedState,
        ),
        recipeSearchState = recipeSearchState,
        randomRecipe = randomRecipe
    )
}

//data class StartRecipe(val randomRecipe: List<RecipeInfo>) {
//    companion object {
//        val initial = StartRecipe(emptyList())
//    }
//}

data class SearchBarState(val searchTerm: String, val expandedState: Boolean) {
    companion object {
        val initial = SearchBarState("", true)
    }
}

sealed class RecipeSearchState {
    data object Idle : RecipeSearchState()
    data class Random(val randomRecipe: RecipeInfo) : RecipeSearchState()
    data object Empty : RecipeSearchState()
    data object Loading: RecipeSearchState()
    data class Success(val recipeList: List<RecipeInfo>): RecipeSearchState()
    data class Error(val message: String) : RecipeSearchState()
}