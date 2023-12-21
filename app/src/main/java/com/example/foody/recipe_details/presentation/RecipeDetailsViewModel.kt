package com.example.foody.recipe_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.domain.model.RecipeInfo
import com.example.foody.recipe_details.domain.RecipeDetailsRepository
import com.example.foody.recipe_details.presentation.model.RecipeDetailsState
import com.example.foody.search.domain.FoodRecipesSearchRepository
import com.example.foody.search.presentation_mvvm.model.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val repository: RecipeDetailsRepository): ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailsState.initialValue)
    val state : StateFlow<RecipeDetailsState> = _state

    fun getRecipeDetails(recipeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val detailsList = repository.getRecipeDetails(recipeId = recipeId)

            withContext(Dispatchers.Main) {
                _state.emit(_state.value.copy(recipeDetails = detailsList ))
            }
        }
    }
}