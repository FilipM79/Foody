package com.example.foody.search.domain

import com.example.foody.domain.model.RecipeInfo

// This is step 5
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6. Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)
interface FoodRecipesSearchRepository {
    suspend fun search(searchTerm: String) : List<RecipeInfo>
}