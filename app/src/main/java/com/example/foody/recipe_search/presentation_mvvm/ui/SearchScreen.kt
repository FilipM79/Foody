package com.example.foody.recipe_search.presentation_mvvm.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foody.recipe_search.presentation_mvvm.SearchNavigationEvent
import com.example.foody.recipe_search.presentation_mvvm.SearchViewModel
import com.example.foody.recipe_search.presentation_mvvm.model.RecipeSearchState
import com.example.foody.recipe_search.presentation_mvvm.model.SearchScreenState
import com.example.foody.shared.domain.model.RecipeInfo

// 12-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

@Composable
fun SearchScreen(
    goToDetailsScreen: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    // for floating button state
    val fabExpandedState = rememberSaveable{ mutableStateOf(true) }
    
    val state by viewModel.state.collectAsState()
    val performSearch: () -> Unit = remember { { viewModel.search() } }
    val onValueChanged: (String) -> Unit = remember { { viewModel.updateSearchTerm(it) } }
    val navigateWith: (String) -> Unit =
        remember { { viewModel.navigateTo(SearchNavigationEvent.ToDetails(it)) } }

    SearchScreenContent(
        state = state,
        fabExpandedState = fabExpandedState,
        performSearch = performSearch,
        navigateWith = navigateWith,
        onValueChanged = onValueChanged
    )

    // Collecting events from the other side of pipeline
    LaunchedEffect(key1 = Unit ) {
        viewModel.navigation.collect { navigationEvent ->
            when(navigationEvent) {
                is SearchNavigationEvent.ToDetails -> {
                    goToDetailsScreen(navigationEvent.recipeId)
                }
            }
        }
    }
}

@Composable
private fun EmptySearchResult() {
    Text(text = "There are no results for this search term. Please try something else.",
        modifier = Modifier.padding(16.dp))
}

@Composable
private fun SearchSuccess(
    mealList: List<RecipeInfo>,
    navigateWith: (recipeId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(240.dp),
        modifier = modifier,
        content = {
        
            items(count = mealList.size, key = { index -> mealList[index].id } // ???
            ) {
                RecipeItem(item = mealList[it]) { recipeId ->
                    // sending one event via viewModel
                    navigateWith(recipeId)
                }
            }
        }
    )
}

@Composable
private fun FloatingSearchButton(
    fabExpandedState: MutableState<Boolean>,
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            FloatingActionButton(onClick = { fabExpandedState.value = !fabExpandedState.value }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
        }
    }
}

@Composable
private fun ErrorMessage(errorMessage: String) { Text(text = errorMessage) }

@Composable
private fun SearchScreenContent(
    state: SearchScreenState,
    fabExpandedState: MutableState<Boolean>,
    performSearch: () -> Unit,
    navigateWith: (recipeId: String) -> Unit,
    onValueChanged: (searchTerm: String) -> Unit
) {
    
    // for dimming the background ...
    val modifier = if(fabExpandedState.value) {
        Modifier
            .background(Color.LightGray)
            .alpha(0.3f)
    } else Modifier
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (fabExpandedState.value) {
            SearchTextFieldAndButton(
                state = state,
                fabExpandedState = fabExpandedState,
                performSearch = performSearch,
                onValueChanged = onValueChanged
            )
        }
        when (state.recipeSearchState) {
            is RecipeSearchState.Idle -> Unit
            is RecipeSearchState.Empty -> EmptySearchResult()
            is RecipeSearchState.Loading -> CircularProgressIndicator(
                modifier = Modifier.requiredSize(72.dp), strokeWidth = 8.dp
            )
            is RecipeSearchState.Success -> {
                SearchSuccess(
                    mealList = state.recipeSearchState.mealList,
                    navigateWith = navigateWith,
                    // for dimming the background ...
                    modifier = modifier
                )
            }
            is RecipeSearchState.Error -> ErrorMessage(errorMessage = state.recipeSearchState.message)
        }
    }
    FloatingSearchButton(fabExpandedState = fabExpandedState)
}

@Composable
private fun RecipeItem(item: RecipeInfo, goToDetailsScreen: (String) -> Unit) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .clickable(enabled = true,
                    // going to details screen by clicking on an individual recipe
                    onClick = { goToDetailsScreen(item.id) })
        ) {
            Box {
                if (!item.imageUrl.isNullOrBlank()) {
                    RecipeImage(imageUrl = item.imageUrl)
                }
                Box(modifier = Modifier.padding(8.dp)) {
                    RecipeTitle(title = item.title)
                }
            }
        }
    }
}

@Composable
private fun SearchTextFieldAndButton(
    state: SearchScreenState,
    fabExpandedState: MutableState<Boolean>,
    performSearch: () -> Unit,
    onValueChanged: (searchTerm: String) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = state.searchTerm,
            onValueChange = { onValueChanged.invoke(it) }, // updates the changes on searchTerm
            modifier = Modifier
                .height(48.dp)
                .weight(1f)
                .clip(shape = RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {
            
            // ????? why doesn't it recompose?
            fabExpandedState.value = true
            performSearch()
            
            when (state.recipeSearchState) {
                is RecipeSearchState.Idle -> {
                    fabExpandedState.value = false
                }
                is RecipeSearchState.Error -> {
                    fabExpandedState.value = true
                }
                is RecipeSearchState.Empty -> {
                    fabExpandedState.value = true
                }
                is RecipeSearchState.Loading -> {
                    fabExpandedState.value = true
                }
                is RecipeSearchState.Success -> {
                    fabExpandedState.value = false
                }
            }
        }
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
private fun RecipeTitle(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.White, 1f to Color.Gray, startY = 20f, endY = 80.0f
                ), alpha = 0.8f
            )
            .padding(8.dp),
        color = Color.Black
    )
}

@Composable
private fun RecipeImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .padding(top = 4.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(border = BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(16.dp))
    )
    Spacer(Modifier.size(8.dp))
}

@Composable
@Preview
private fun Preview() {
    SearchScreenContent(
        state = SearchScreenState.initialValue,
        fabExpandedState = remember { mutableStateOf(true) },
        performSearch = { },
        navigateWith = { },
        onValueChanged = { },
    )
}