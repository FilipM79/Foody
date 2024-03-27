package com.example.foody.recipe_search.presentation_mvvm

import com.example.foody.shared.domain.model.Ingredient
import com.example.foody.shared.domain.model.RecipeInfo


fun getRecipeListTestData(numberOfRecipes: Int) : List<RecipeInfo> {
    
    val listOfRecipes = mutableListOf<RecipeInfo>()
    
    for (i in 1..numberOfRecipes) {
        
        listOfRecipes.add(
            RecipeInfo(
                id = i.toString(),
                title = "Recipe Tile",
                cuisine = "Recipe Cuisine",
                category = "Recipe Category",
                ingredients = listOf(
                    Ingredient(title = "Ingredient 1", measure = "1 cup")
                ),
                recipe = "Recipe Details",
                imageUrl = null,
                videoUrl = null,
                tags = listOf("First")
            )
        )
    }
    return listOfRecipes
}
