package com.example.foody.shared.domain.model

// 6-th
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// Here we define an Ingredient data class with two main properties
// This is then needed in RecipeInfo
data class Ingredient(
    val title: String,
    val measure: String
)
