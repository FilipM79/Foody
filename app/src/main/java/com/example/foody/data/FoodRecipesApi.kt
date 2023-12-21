package com.example.foody.data

import com.example.foody.search.data.model.RecipeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit interface
// It communicates with recipes Rest API theMealDb (RE-presentational, S-tate, T-ransfer)
// This will contain all separate endpoint functions for different endpoint calls for each CRUD operations
interface FoodRecipesApi {
    @GET("/api/json/v1/1/search.php")
    suspend fun search(
        @Query("s") searchTerm: String,
    ) : Response<RecipeSearchResponse>
}

