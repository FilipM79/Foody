package com.example.foody.recipe_details.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun RecipeDetailsScreen(
    recipeViewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    val recipeId = recipeViewModel.recipeId
    Log.d("RecipeDetailsScreen", "... val recipeId is -$recipeId -...")

    recipeViewModel.getRecipeDetails(recipeId = recipeId)
    Log.d("RecipeDetailsScreen", "... after getRecipeDetails")

    val state by recipeViewModel.state.collectAsState()
    RecipeDetailsItem(item = state.recipeDetails)
}

@Composable
fun RecipeDetailsItem(item: RecipeInfo) {
    Log.d("RecipeDetailsScreen", "... beginning of fun RecipeDetailsItem")
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

        Log.d("RecipeDetailsScreen", "... end of fun RecipeDetailsItem")

    }
}