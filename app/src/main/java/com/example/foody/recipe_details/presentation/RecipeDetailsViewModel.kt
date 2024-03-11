package com.example.foody.recipe_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.recipe_details.presentation.model.RecipeDetailsState
import com.example.foody.recipe_details.presentation.model.RecipeInfoState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// providing a view model factory class to HiltViewModel in order to get args from RecipeFragment
// via goToDetailsScreen (navigating with args)
@HiltViewModel(assistedFactory = RecipeDetailsViewModelFactory::class)
class RecipeDetailsViewModel @AssistedInject constructor(
    // defining hilt how to obtain args via assisted injection
    @Assisted private val recipeId: String,
    private val repository: RecipeDetailsSearchRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState.initialValue)
    val state: StateFlow<RecipeDetailsState> = _state

    init {
        // getting recipe details when instantiating a viewmodel
        getRecipeDetails()
    }

    private fun getRecipeDetails() {
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

// this recipeId is defined when we instantiate a viewmodel in RecipeDetailsFragment via
// factory.create in extrasProducer
@AssistedFactory
interface RecipeDetailsViewModelFactory {
    fun create(recipeId: String): RecipeDetailsViewModel
}
