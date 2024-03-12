package com.example.foody.recipe_search.data.model

import com.example.foody.shared.domain.model.RecipeResult
import com.google.gson.annotations.SerializedName

// 3-rd step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// This is the data class with the list of possible search items we could get from Api
data class RecipesSearchResponse(
    @SerializedName("meals")
    val recipes: List<RecipeResult>?
)