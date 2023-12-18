package com.example.foody.search

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodRecipesSearchApi {

//    @GET("/recipes/findByIngredients")
//    suspend fun searchByIngredients(
//        @Query("ingredients") ingredients: String,
//        @Query("number") pageSize: Int
//    ) : Response<SearchResult>

    @GET("/recipes/complexSearch?")
    suspend fun search(
        @Query("query") searchTerm: String,
        @Query("number") pageSize: Int,
        @Query("offset") startingPosition: Int
    ) : Response<SearchResult>
}

