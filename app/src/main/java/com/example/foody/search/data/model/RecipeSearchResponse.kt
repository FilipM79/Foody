package com.example.foody.search.data.model

import com.example.foody.shared.domain.model.RecipeItemResponse
import com.google.gson.annotations.SerializedName

// This is step 3
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)
data class RecipeSearchResponse(
    @SerializedName("meals")
    val recipes: List<RecipeItemResponse>
)