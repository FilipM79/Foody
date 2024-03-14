package com.example.foody.recipe_search.domain

import com.example.foody.shared.domain.model.RecipeInfo

// 5-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// In this interface we define a search function which returns a list of recipes
interface RecipesSearchRepository {
    suspend fun search(searchTerm: String) : List<RecipeInfo>
    suspend fun randomRecipe() : RecipeInfo
}