package com.example.foody.recipe_details.presentation.ui

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foody.shared.domain.model.RecipeInfo
import com.example.foody.recipe_details.presentation.RecipeDetailsViewModel
import com.example.foody.recipe_details.presentation.model.RecipeInfoState

@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    recipeViewModel: RecipeDetailsViewModel = hiltViewModel()
) {

    // Way 2
    LaunchedEffect(key1 = recipeId) {
        recipeViewModel.getRecipeDetails(recipeId)
    }

//    // Way 3
//    rememberSaveable {
//        recipeViewModel.getRecipeDetails(recipeId)
//        ""
//    }

    // Way 4 is via SavedStateHandle

    // 5-th way is via Custom State Handler Implementation

    val state by recipeViewModel.state.collectAsState()
    when (val detailsState = state.detailsState) { // Biranje po tipu
        is RecipeInfoState.RecipeInfoValue -> RecipeDetailsItem(item = detailsState.recipeDetails)
        is RecipeInfoState.RecipeInfoLoading -> CircularProgressIndicator()
        is RecipeInfoState.RecipeInfoError -> ErrorMessage(errorMessage = detailsState.message)
    }
}

@Composable
fun ErrorMessage(errorMessage: String) {
    Text(text = errorMessage)
}

@Composable
fun RecipeDetailsItem(item: RecipeInfo) {
    Log.e("RecipeDetailsScreen", "... beginning of fun RecipeDetailsItem")
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

        Log.e("RecipeDetailsScreen", "... end of fun RecipeDetailsItem")

    }
}