package com.example.foody.recipe_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.recipe_details.presentation.model.RecipeDetailsState
import com.example.foody.recipe_details.presentation.model.RecipeInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeDetailsSearchRepository,
): ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState.initialValue)
    val state : StateFlow<RecipeDetailsState> = _state

    fun getRecipeDetails(recipeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _state.emit(_state.value.copy(detailsState = RecipeInfoState.RecipeInfoLoading))
            }

            val newState = try {
                val recipeDetails = repository.getRecipeDetails(recipeId = recipeId)
                RecipeInfoState.RecipeInfoValue(recipeDetails)
            } catch (e: Exception) {
                RecipeInfoState.RecipeInfoError(e.message ?: "Unknown error from RecipeDetailsVM")
            }

            withContext(Dispatchers.Main) {
                _state.emit(_state.value.copy(detailsState = newState))
            }
        }
    }
}