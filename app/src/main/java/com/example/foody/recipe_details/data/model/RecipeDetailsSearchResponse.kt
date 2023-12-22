package com.example.foody.recipe_details.data.model

import com.example.foody.shared.domain.model.RecipeItemResponse
import com.google.gson.annotations.SerializedName

data class RecipeDetailsSearchResponse(
    @SerializedName("meals")
    val recipe: RecipeItemResponse
)