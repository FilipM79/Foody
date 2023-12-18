package com.example.foody.search

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("results")
    val recipes: List<RecipeSearchResult>,

    @SerializedName("offset")
    val startPosition: Int,

    @SerializedName("number")
    val resultCountLimit: Int,

    @SerializedName("totalResults")
    val totalResultCount: Int
)
