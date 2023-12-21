package com.example.foody.domain.model

data class RecipeInfo(
    val id: String,
    val title: String,
    val cuisine: String,
    val category: String,
    val ingredients: List<Ingredient>,
    val recipe: String,
    val imageUrl: String,
    val videoUrl: String?,
//    val dateModified: Any,
    val tags: List<String>,
)