package com.example.foody.recipe_details.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.recipe_details.presentation.model.RecipeDetailsState
import com.example.foody.recipe_details.presentation.model.RecipeInfoState
import com.example.foody.shared.ClickedRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.NullPointerException

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeDetailsSearchRepository,
    private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState.initialValue)
    val state : StateFlow<RecipeDetailsState> = _state
//    private val recipeId = getSavedRecipeId()
//    private val recipeId = ClickedRecipe.getId()

//    init {
//        getRecipeDetails(recipeId)
//    }

    fun getRecipeDetails(recipeId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            Log.e("RecipeDetailsViewModel", "recipeId is $recipeId")
            val newState = try {
                val recipeDetails = repository.getRecipeDetails(recipeId = recipeId)
                RecipeInfoState.RecipeInfoValue(recipeDetails)
            } catch (e: Exception) {
                RecipeInfoState.RecipeInfoError(e.message ?: "Unknown error from RecipeDetailsVM")
            }
            Log.e("RecipeDetailsViewModel", "...after val recipeDetails")

            withContext(Dispatchers.Main) {
                Log.d("RecipeDetailsViewModel", "... before state emit")
                _state.emit(
                    _state.value.copy(detailsState = newState)
                )
                Log.e("RecipeDetailsViewModel", "... after state emit")
            }
        }
    }

    private fun getSavedRecipeId(): String? {
        return savedStateHandle.get<String>("recipeId")
    }
}