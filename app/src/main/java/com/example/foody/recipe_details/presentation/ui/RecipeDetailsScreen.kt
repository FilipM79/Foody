package com.example.foody.recipe_details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foody.recipe_details.presentation.RecipeDetailsViewModel
import com.example.foody.recipe_details.presentation.model.RecipeInfoState
import com.example.foody.shared.domain.model.RecipeInfo

@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    recipeViewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    // 1-st way to get recipeId from previous Fragment is via LaunchedEffect.
    // Because in here we only do this once
    LaunchedEffect(key1 = recipeId) { recipeViewModel.getRecipeDetails(recipeId) }

//    // 2-nd way would be to pass a viewModel to Fragment directly
//    // 3-rd way is via remember block for the same reason as in 1-st
//    rememberSaveable { recipeViewModel.getRecipeDetails(recipeId) }
//    // 4-th way would be via SavedStateHandle
//    // 5-th way would be via Custom State Handler Implementation

    val state by recipeViewModel.state.collectAsState()
    when (val detailsState = state.detailsState) { // Biranje po tipu
        is RecipeInfoState.RecipeInfoValue -> RecipeDetailsItem(item = detailsState.recipeDetails)
        is RecipeInfoState.RecipeInfoLoading -> CircularProgressIndicator()
        is RecipeInfoState.RecipeInfoError -> ErrorMessage(errorMessage = detailsState.message)
    }
}

@Composable
fun ErrorMessage(errorMessage: String) { Text(text = errorMessage) }

@Composable
fun RecipeDetailsItem(item: RecipeInfo) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = item.id,
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
        Spacer(Modifier.size(8.dp))
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
        Spacer(Modifier.size(8.dp))
        Text(
            text = item.cuisine,
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
        Spacer(Modifier.size(8.dp))
        Text(
            text = item.recipe,
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
        Spacer(Modifier.size(8.dp))
        Text(
            text = item.category,
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
        Spacer(Modifier.size(8.dp))
    }
}