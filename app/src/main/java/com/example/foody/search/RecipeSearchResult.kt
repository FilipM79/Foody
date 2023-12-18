package com.example.foody.search

import com.google.gson.annotations.SerializedName

data class RecipeSearchResult(
    val id: Int,
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    val imageType: String
)