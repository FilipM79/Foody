package com.example.foody.recipe_search.presentation_mvvm.model

import com.example.foody.shared.domain.model.Ingredient
import com.example.foody.shared.domain.model.RecipeInfo

// 10-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

data class SearchScreenState(
    val searchBarState: SearchBarState,
    val recipeSearchState: RecipeSearchState,
    val startRecipe: RecipeInfo
) {
    companion object {
        val initialValue = SearchScreenState(
            searchBarState = SearchBarState.initial,
            startRecipe = StartRecipe.initial.random,
            recipeSearchState = RecipeSearchState.Idle(StartRecipe.initial.random),
        )
        
    }
    
    fun clone(
        searchTerm: String = searchBarState.searchTerm,
        searchBarExpandedState: Boolean = searchBarState.expandedState,
        recipeSearchState: RecipeSearchState = this.recipeSearchState,
        startRecipe: RecipeInfo = this.startRecipe
    ) = copy(
        searchBarState = searchBarState.copy(
            searchTerm = searchTerm,
            expandedState = searchBarExpandedState,
        ),
        recipeSearchState = recipeSearchState,
        startRecipe = startRecipe
    )
}

data class StartRecipe(val random: RecipeInfo) {
    companion object {
        val initial = StartRecipe(
            random = RecipeInfo(
                title = "Random recipe",
                cuisine = "Italian",
                category = "Breakfast",
                id = "123",
                imageUrl = "",
                tags = listOf("1", "2"),
                ingredients = listOf(Ingredient(title = "Ing 1", measure = "1 tbs")),
                recipe = "Enjoy",
                videoUrl = ""
            )
        )
    }
}

data class SearchBarState(val searchTerm: String, val expandedState: Boolean) {
    companion object {
        val initial = SearchBarState("", true)
    }
}

sealed class RecipeSearchState {
    data class Idle(val randomRecipe: RecipeInfo) : RecipeSearchState()
    data object Empty : RecipeSearchState()
    data object Loading: RecipeSearchState()
    data class Success(val mealList: List<RecipeInfo>): RecipeSearchState()
    data class Error(val message: String) : RecipeSearchState()
}