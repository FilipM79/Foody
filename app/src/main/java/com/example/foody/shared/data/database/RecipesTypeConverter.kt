package com.example.foody.shared.data.database

import androidx.room.TypeConverter
import com.example.foody.search.data.model.RecipesSearchResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// we can not store complex objects in local database directly, so we need to convert them to String
class RecipesTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun recipeToString(recipe: RecipesSearchResponse): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(data: String): RecipesSearchResponse {
        val listType = object : TypeToken<RecipesSearchResponse>() {}.type
        return  gson.fromJson(data, listType)
    }
}