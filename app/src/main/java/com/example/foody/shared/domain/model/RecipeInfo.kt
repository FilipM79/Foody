package com.example.foody.shared.domain.model

// This is step 7
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6. Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

data class RecipeInfo(
    val id: String,
    val title: String,
    val cuisine: String,
    val category: String,
    val ingredients: List<Ingredient>,
    val recipe: String,
    val imageUrl: String?,
    val videoUrl: String?,
    val tags: List<String>,
)