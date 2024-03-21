package com.example.foody.recipe_search.presentation_mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.recipe_search.domain.RecipesSearchRepository
import com.example.foody.recipe_search.presentation_mvvm.model.RecipeSearchState
import com.example.foody.recipe_search.presentation_mvvm.model.SearchScreenState
import com.example.foody.shared.domain.model.RecipeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// 11-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecipesSearchRepository,
): ViewModel() {
    
    private val _state = MutableStateFlow(SearchScreenState.initialValue)
    val state : StateFlow<SearchScreenState> = _state
    
    private val _navigation = Channel<SearchNavigationEvent>()
    val navigation: Flow<SearchNavigationEvent> = _navigation.receiveAsFlow()
    
    init {
        showRandomRecipe()
    }

    fun navigateTo(event: SearchNavigationEvent) { viewModelScope.launch { _navigation.send(event) }}

    fun search() {
        fetchRecipeListAndSearchBarState(
            // important to do a lambda "{ }" call for repositoryFunction
            repositoryFunction = { repository.search(state.value.searchBarState.searchTerm) },
//            expandedState = ::shouldBarBeExpandedState // this way "::" we are passing a function as lambda
            expandedState = { shouldBarBeExpandedState(it) }
        )
    }
    
    fun showRandomRecipe() {
        fetchRecipeListAndSearchBarState( { repository.randomRecipe() }, { true })
    }
    
    private fun fetchRecipeListAndSearchBarState(
        repositoryFunction: suspend () -> List<RecipeInfo>, // must be suspend in order to call a suspend fun
        expandedState: (RecipeSearchState) -> Boolean
    ) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(recipeSearchState = RecipeSearchState.Loading))
    
            val newRecipeSearchState = try {
                val recipeList = withContext(Dispatchers.IO) {
                    repositoryFunction() // calling lambda here, instead when passing it
                }
        
                if (recipeList.isEmpty()) RecipeSearchState.Empty
                else RecipeSearchState.Success(recipeList = recipeList)
            } catch (e: Exception) {
                Log.e("RecipeSearchViewModel", e.message.orEmpty(), e)
                RecipeSearchState.Error("Unknown error from search.")
            }
    
            _state.emit(
                _state.value.clone(
                    recipeSearchState = newRecipeSearchState,
                    searchBarExpandedState = expandedState(newRecipeSearchState),
                    searchTerm = ""
                )
            )
        }
    }
    
    fun updateSearchTerm(searchTerm: String) {
        viewModelScope.launch {
            _state.emit(
                _state.value.clone(searchTerm = searchTerm)
            )
        }
    }
    
    private fun shouldBarBeExpandedState(recipeSearchState: RecipeSearchState) : Boolean =
         when (recipeSearchState) {
             is RecipeSearchState.Idle -> true
             is RecipeSearchState.Error -> true
             is RecipeSearchState.Empty -> true
             is RecipeSearchState.Loading -> true
             is RecipeSearchState.Success -> !state.value.searchBarState.expandedState
         }
    
    fun flipSearchBarExpandedState() {
        viewModelScope.launch {
            _state.emit(
                _state.value.clone(searchBarExpandedState = !state.value.searchBarState.expandedState)
            )
        }
    }
}

sealed class SearchNavigationEvent {
    data class ToDetails(val recipeId: String): SearchNavigationEvent()
}