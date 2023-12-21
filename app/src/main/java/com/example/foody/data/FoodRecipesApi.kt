package com.example.foody.data

import com.example.foody.search.data.model.RecipeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// This is step 4
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6. Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// Retrofit interface
// It communicates with recipes REST API theMealDb (RE-presentational, S-tate, T-ransfer)
// This will contain all separate endpoint functions for different endpoint calls for each CRUD operations
interface FoodRecipesApi {
    @GET("/api/json/v1/1/search.php")
    suspend fun search(
        @Query("s") searchTerm: String
    ) : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getRecipeDetails(
        @Query("i") recipeId: String
    ) : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/search.php")
    suspend fun listAllMealsByLetter(
        @Query("f") letter: String
    ) : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/random.php")
    suspend fun singleRandomMeal() : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/categories.php")
    suspend fun listDetailedMealCategories() : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/list.php?c=list")
    suspend fun listAllMealCategories() : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/list.php?a=list")
    suspend fun listAllAreas() : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/list.php?i=list")
    suspend fun listAllIngredients() : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/filter.php")
    suspend fun filterAllMealsByMainIngredient(
        @Query("i") mainIngredient: String
    ) : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/filter.php")
    suspend fun filterAllMealsByCategory(
        @Query("c") category: String
    ) : Response<RecipeSearchResponse>

    @GET("/api/json/v1/1/filter.php")
    suspend fun filterAllMealsByArea(
        @Query("a") area: String
    ) : Response<RecipeSearchResponse>

}

