package com.example.foody.search.domain

import com.example.foody.domain.model.RecipeInfo

interface FoodRecipesSearchRepository {
    suspend fun search(searchTerm: String) : List<RecipeInfo>
}