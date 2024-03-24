package com.example.foody.recipe_search.presentation_mvvm.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.foody.recipe_search.presentation_mvvm.model.RecipeListState
import com.example.foody.recipe_search.presentation_mvvm.model.SearchScreenState
import com.example.foody.shared.domain.model.RecipeInfo

// 12-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

@Suppress("SuspiciousCallableReferenceInLambda")
@Composable
fun SearchScreen(
    goToDetailsScreen: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val performSearch: () -> Unit = remember { viewModel::search }
    val generateRandomRecipe: () -> Unit = remember { viewModel::showRandomRecipe }
    val onValueChanged: (String) -> Unit = remember { viewModel::updateSearchTerm }
    val navigateWith: (String) -> Unit =
        remember { { viewModel.navigateTo(SearchNavigationEvent.ToDetails(it)) } }
    val onFabClick: () -> Unit = remember { viewModel::flipSearchBarExpandedState }

    SearchScreenContent(
        state = state,
        performSearch = performSearch,
        generateRandomRecipe = generateRandomRecipe,
        navigateWith = navigateWith,
        onValueChanged = onValueChanged,
        onFabClick = onFabClick
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
private fun SearchScreenContent(
    state: SearchScreenState,
    performSearch: () -> Unit,
    generateRandomRecipe: () -> Unit,
    navigateWith: (recipeId: String) -> Unit,
    onValueChanged: (searchTerm: String) -> Unit,
    onFabClick: () -> Unit
) {
    // for dimming the background ...
    // remember, but change if expandedState changed ...
    val dimmingModifier = remember(state.searchBarState.expandedState) {
        if (state.searchBarState.expandedState) {
            Modifier.background(Color.LightGray).alpha(0.3f)
        } else Modifier
    }
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (state.searchBarState.expandedState) {
            SearchTextFieldAndButton(
                state = state,
                performSearch = performSearch,
                onValueChanged = onValueChanged,
                generateRandomRecipe = generateRandomRecipe
            )
        }
        when (state.recipeListState) {
            is RecipeListState.Idle -> Unit
            is RecipeListState.Empty -> EmptySearchResult()
            is RecipeListState.Loading -> CircularProgressIndicator(
                modifier = Modifier.requiredSize(72.dp), strokeWidth = 8.dp
            )
            is RecipeListState.Success -> {
                SearchSuccess(
                    recipeList = state.recipeListState.recipeList,
                    navigateWith = navigateWith,
                    // for dimming the background ...
                    modifier = dimmingModifier
                )
            }
            is RecipeListState.Error -> ErrorMessage(errorMessage = state.recipeListState.message)
        }
    }
    FloatingSearchButton(onFabClick)
}

@Composable
private fun FloatingSearchButton(
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FloatingActionButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
private fun SearchTextFieldAndButton(
    state: SearchScreenState,
    performSearch: () -> Unit,
    onValueChanged: (searchTerm: String) -> Unit,
    generateRandomRecipe: () -> Unit
    ) {
    Column {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = state.searchBarState.searchTerm,
                onValueChange = { onValueChanged.invoke(it) }, // updates the changes on searchTerm
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = performSearch) {
                Text(text = "Search")
            }
        }
        Button(onClick = generateRandomRecipe,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Generate random recipe")
        }
    }
}

@Composable
fun ShowOneRandomRecipe(
    navigateWith: (recipeId: String) -> Unit,
    randomRecipeList: List<RecipeInfo>,
) {
    if (randomRecipeList.isNotEmpty()) {
        RecipeItem(item = randomRecipeList[0], goToDetailsScreen = navigateWith)
    }
}

@Composable
private fun SearchSuccess(
    recipeList: List<RecipeInfo>,
    navigateWith: (recipeId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if(recipeList.size == 1) {
        ShowOneRandomRecipe(navigateWith = navigateWith, randomRecipeList = recipeList)
    } else {
        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            modifier = modifier,
            content = {
                items(count = recipeList.size, key = { index -> recipeList[index].id } // ???
                ) {
                    RecipeItem(item = recipeList[it]) { recipeId ->
                        // sending one event via viewModel
                        navigateWith(recipeId)
                    }
                }
            }
        )
    }
}

@Composable
private fun ErrorMessage(errorMessage: String) { Text(text = errorMessage) }

@Composable
private fun EmptySearchResult() {
    Text(text = "There are no results for this search term. Please try something else.",
        modifier = Modifier.padding(16.dp))
}

@Composable
private fun RecipeItem(item: RecipeInfo, goToDetailsScreen: (String) -> Unit) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
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
private fun RecipeTitle(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.White, 1f to Color.Gray, startY = 20f, endY = 80.0f
                ), alpha = 0.6f
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
        performSearch = { },
        generateRandomRecipe = { },
        navigateWith = { },
        onValueChanged = { },
        onFabClick = { }
    )
}