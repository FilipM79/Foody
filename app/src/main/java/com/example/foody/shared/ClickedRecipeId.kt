package com.example.foody.shared

object ClickedRecipe {

    private var _recipeId = ""

    fun saveId(recipeId: String) { _recipeId = recipeId }

    fun getId(): String { return _recipeId }
}