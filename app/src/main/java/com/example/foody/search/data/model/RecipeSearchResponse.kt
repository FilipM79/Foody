package com.example.foody.search.data.model

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("meals")
    val meals: List<RecipeItemResponse>
)