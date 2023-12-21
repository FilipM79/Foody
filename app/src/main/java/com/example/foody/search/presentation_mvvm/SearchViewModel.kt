package com.example.foody.search.presentation_mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SearchViewModel @Inject constructor(private val repository: FoodRecipesSearchRepository): ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState.initialValue)
    val state : StateFlow<SearchScreenState> = _state

    fun search(searchTerm: String) {
        viewModelScope.launch(Dispatchers.IO) {  // prelazak na IO thread
            val mealList = repository.search(searchTerm)

            // Pojedinacni emit predstavlja jedan event tipa SearchScreenState koji flow emituje dalje
            // To se zatim kolektuje kao state u compose-u i dolazi do rekompozicije

            // ovako smo uradili radi efikasnijeg koriscenja memorije
            // (i zato sto zelimo da ostavimo ostale eventualne delove state-a nepromenjene)
            withContext(Dispatchers.Main) {// povratak na main thread ...
                _state.emit(_state.value.copy(recipes = mealList))
            }
        }
    }
}