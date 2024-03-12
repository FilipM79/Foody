package com.example.foody.recipe_details.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foody.recipe_details.presentation.RecipeDetailsViewModel
import com.example.foody.recipe_details.presentation.model.RecipeInfoState
import com.example.foody.shared.domain.model.Ingredient
import com.example.foody.shared.domain.model.RecipeInfo
import com.example.foody.shared.presentation.assistedHiltViewModel
import com.example.foody.shared.util.Constants

// Passing the recipeId (coming from previous screen) to ViewModel:
// 1-st way is to set it (or use it) from here (Compose) via exposed public ViewModel function,
//  but in a way which makes sure it is only called once (and not on every composition):
//   - LaunchedEffect(key1 = recipeId) { recipeViewModel.getRecipeDetails(recipeId) }, or
//   - rememberSaveable { recipeViewModel.getRecipeDetails(recipeId) }
// 2-nd way is to inject the viewModel into Fragment and pass it to Compose function,
//    and before passing it to set the recipeId (will be once),
//    or to use assisted injection, since it is available from Fragment injection
//    (assisted injection for Compose hiltViewModel function is also coming soon).
// 3-rd - via SavedStateHandle (which is available to ViewModel, so doesn't need assisted inject)
// 4-th - via custom state handler implementation - some singleton class
//  with responsibility to hold the state until passed, and clear it once consumed.
@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    recipeViewModel: RecipeDetailsViewModel = assistedHiltViewModel(recipeId)
) {
    val state by recipeViewModel.state.collectAsState()

    when (val detailsState = state.detailsState) {
        is RecipeInfoState.Value -> RecipeDetailsItem(item = detailsState.recipeDetails)
        is RecipeInfoState.Loading -> CircularProgressIndicator(
            modifier = Modifier.requiredSize(72.dp),
            strokeWidth = 8.dp
        )
        is RecipeInfoState.Error -> ErrorMessage(errorMessage = detailsState.message)
    }
}

@Composable
private fun ErrorMessage(errorMessage: String) { Text(text = errorMessage) }

@Composable
private fun RecipeDetailsItem(item: RecipeInfo) {
    Column {
        RecipeTitleText(title = item.title)
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (!item.imageUrl.isNullOrBlank()) {
                RecipeImage(imageUrl = item.imageUrl)
            }
            TextItem(item = item.cuisine, fieldTitle = "Cuisine")
            TextItem(item = item.category, fieldTitle = "Category")
            RecipeText(recipeText = item.recipe, fieldTitle = "Recipe")
            Ingredients(ingredients = item.ingredients)
        }
    }
}

@Composable
@Preview
private fun ItemPreview() {
    RecipeDetailsItem(item = RecipeInfo(
        id = "333",
        title = "Pie",
        cuisine = "Italian",
        category = "Pastry",
        recipe = "Cook for 12 minutes",
        ingredients = emptyList(),
        tags = emptyList(),
        imageUrl = "",
        videoUrl = ""
    ))
}

@Composable
fun RecipeTitleText(title: String) {
    Spacer(Modifier.size(8.dp))

    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp)
            .background(
                brush = Brush.verticalGradient(
                    0.3f to Color.White,
                    0.8f to Color.Gray,
                    startY = 0f,
                    endY = 80.0f
                )
            )
    )
    Spacer(Modifier.size(2.dp))
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
    Spacer(Modifier.size(2.dp))
}

@Composable
private fun TextItem(item: String, fieldTitle: String) {
    Text(
        text = "$fieldTitle: $item",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(Color.White)
            .padding(2.dp)
    )
    Spacer(Modifier.size(2.dp))
}

@Composable
private fun RecipeText(recipeText: String, fieldTitle: String) {
    Text(
        text = "$fieldTitle:\n$recipeText",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(Color.White)
            .padding(2.dp)
    )
    Spacer(Modifier.size(2.dp))
}

@Composable
private fun Ingredients(ingredients: List<Ingredient>) {
    Column {
        Text(
            text = "\nIngredients:\n",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        LazyHorizontalGrid(
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(0.dp),
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .height(430.dp),
            content = {
                items(
                    count = ingredients.size,
                    key = null // ???
                ) {
                    Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Card(elevation = CardDefaults.elevatedCardElevation(8.dp)) {
                            AsyncImage(
                                model = "${Constants.BASE_URL}/images/ingredients/${ingredients[it].title}-Small.png",
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(shape = RoundedCornerShape(16.dp))
                                    .fillMaxWidth()
                                    .border(
                                        border = BorderStroke(2.dp, Color.Gray),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(16.dp)

                            )
                        }
                        Box(modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = ingredients[it].title,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .fillMaxWidth()
                                        .background(
                                            brush = Brush.verticalGradient(
                                                0f to Color.White,
                                                1f to Color.Gray,
                                                startY = 20f,
                                                endY = 80.0f
                                            ), alpha = 0.7f
                                        )
                                        .padding(4.dp),
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = ingredients[it].measure,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .fillMaxWidth()
                                        .background(
                                            brush = Brush.verticalGradient(
                                                0f to Color.White,
                                                1f to Color.Gray,
                                                startY = 20f,
                                                endY = 80.0f
                                            ), alpha = 0.7f
                                        )
                                        .padding(4.dp),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}