package com.example.foody.search.presentation_mvvm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.foody.R
import com.example.foody.domain.model.RecipeInfo
import com.example.foody.recipe_details.presentation.RecipeDetailsViewModel
import com.example.foody.search.presentation_mvvm.SearchViewModel

// This is step 12
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6. Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    viewModel2: RecipeDetailsViewModel = hiltViewModel()
) {

    var searchTerm by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row() {
            TextField(
                value = searchTerm,
                onValueChange = { searchTerm = it },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = { viewModel.search(searchTerm) }
            ) {
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        val state by viewModel.state.collectAsState()
        Box(modifier = Modifier.clip(shape = RoundedCornerShape(16.dp))) {
            Image(
                painter = painterResource(R.drawable._23),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.07f
            )
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                items(
                    count = state.recipes.size,
                    key = { index -> state.recipes[index].id } // ???
                ) {
                    RecipeItem(item = state.recipes[it], viewModel = viewModel2) // ???
                }
            }
        }
    }
}

@Composable
fun RecipeItem(item: RecipeInfo, viewModel: RecipeDetailsViewModel) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable(enabled = true, onClick = {
                viewModel.getRecipeDetails(item.id)

            })
    ) {
        Card(elevation = CardDefaults.elevatedCardElevation(8.dp)) {
            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(Color.White)
//                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp)
            )
        }
        Spacer(Modifier.size(8.dp))
        Card(elevation = CardDefaults.elevatedCardElevation(8.dp)) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
//                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(16.dp))
            )
        }
    }
}