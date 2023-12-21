package com.example.foody.recipe_details.data.model

import com.example.foody.domain.model.RecipeItemResponse
import com.google.gson.annotations.SerializedName

data class RecipeDetailsResponse(
    @SerializedName("meals")
    val recipe: RecipeItemResponse
)