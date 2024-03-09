package com.example.foody.search.presentation_mvvm.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.foody.search.presentation_mvvm.SearchNavigationEvent
import com.example.foody.search.presentation_mvvm.SearchViewModel
import com.example.foody.search.presentation_mvvm.model.SearchScreenState
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
    val state by viewModel.state.collectAsState()

    val performSearch: () -> Unit = remember { { viewModel.search() } }
    val onValueChanged: (String) -> Unit = remember { { viewModel.updateSearchTerm(it) } }
    val navigateWith: (String) -> Unit =
        remember { { viewModel.navigateTo(SearchNavigationEvent.ToDetails(it)) } }

    SearchScreenContent(
        state = state,
        performSearch = performSearch,
        navigateWith = navigateWith,
        onValueChanged = onValueChanged
    )

    // Ovo je kolektovanje (primanje) eventa sa druge strane Pipeline-a
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
    navigateWith: (recipeId: String) -> Unit,
    onValueChanged: (searchTerm: String) -> Unit
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(240.dp),
        content = {
            item {
                SearchTextFieldAndButton(state, performSearch, onValueChanged)
            }
            items(
                count = state.recipes.size,
                key = { index -> state.recipes[index].id } // ???
            ) {
                RecipeItem(item = state.recipes[it]) { recipeId ->
                    // ovako idemo preko viewModel-a
                    // ovo nam salje jedan event (Channel.send())
                    navigateWith(recipeId)
                }
            }
        }
    )
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
                .clickable(
                    enabled = true,
                    // klikom na recept idemo na detalje
                    onClick = { goToDetailsScreen(item.id) }
                )
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
    performSearch: () -> Unit,
    onValueChanged: (searchTerm: String) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
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
        Button(onClick = { performSearch() }) { Text(text = "Search") }
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
            .background(brush = Brush.verticalGradient(
                0f to Color.White,
                1f to Color.Gray,
                startY = 20f,
                endY = 80.0f
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
            .border(border = BorderStroke(2.dp, Color.Gray,), shape = RoundedCornerShape(16.dp))
    )
    Spacer(Modifier.size(8.dp))
}

@Composable
@Preview
private fun Preview() {
    SearchScreenContent(
        state = SearchScreenState(searchTerm = "", recipes = emptyList()),
        performSearch = { Unit },
        navigateWith = { Unit },
        onValueChanged = { Unit },
    )
}