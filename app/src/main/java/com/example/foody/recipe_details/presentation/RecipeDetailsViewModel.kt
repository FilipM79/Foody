package com.example.foody.recipe_details.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.recipe_details.presentation.model.RecipeDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val repository: RecipeDetailsSearchRepository): ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState.initialValue)
    val state : StateFlow<RecipeDetailsState> = _state
    var recipeId by mutableStateOf("52802")

    fun getRecipeDetails(recipeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("RecipeDetailsViewModel", "recipeId is $recipeId")
            val recipeDetails = repository.getRecipeDetails(recipeId = recipeId)
            Log.d("RecipeDetailsViewModel", "...after val recipeDetails")

            withContext(Dispatchers.Main) {
                Log.d("RecipeDetailsViewModel", "... before state emit")
                _state.emit(_state.value.copy(recipeDetails = recipeDetails))
                Log.d("RecipeDetailsViewModel", "... after state emit")

            }
        }
    }
}