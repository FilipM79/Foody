package com.example.foody.shared.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.recipe_search.data.model.RecipesSearchResponse
import com.example.foody.shared.util.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var recipes: RecipesSearchResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}