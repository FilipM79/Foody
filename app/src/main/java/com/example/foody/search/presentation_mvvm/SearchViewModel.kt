package com.example.foody.search.presentation_mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.search.domain.FoodRecipesSearchRepository
import com.example.foody.search.presentation_mvvm.model.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
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
    private val repository: FoodRecipesSearchRepository,
): ViewModel() {
    private val _state = MutableStateFlow(SearchScreenState.initialValue)
    val state : StateFlow<SearchScreenState> = _state


    private val _navigation = Channel<SearchNavigationEvent>()
    val navigation: Flow<SearchNavigationEvent> = _navigation.receiveAsFlow()

    fun navigateTo(event: SearchNavigationEvent) { viewModelScope.launch { _navigation.send(event) }}

    fun search() {
        // Pojedinacni emit predstavlja jedan event tipa SearchScreenState koji flow emituje dalje
        // To se zatim kolektuje kao state u compose-u i dolazi do rekompozicije
        // ovako smo uradili radi efikasnijeg koriscenja memorije
        // (i zato sto zelimo da ostavimo ostale eventualne delove state-a nepromenjene)

        viewModelScope.launch(Dispatchers.IO) {  // prelazak na IO thread
            val mealList = repository.search(state.value.searchTerm)

            withContext(Dispatchers.Main) {// povratak na main thread ...
                _state.emit(_state.value.copy(recipes = mealList))
            }
        }
    }

    fun updateSearchTerm(searchTerm: String) {
        viewModelScope.launch {
            _state.emit(_state.value.copy(searchTerm = searchTerm))
        }
    }
}

sealed class SearchNavigationEvent {
    data class ToDetails(val recipeId: String): SearchNavigationEvent()
}