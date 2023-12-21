package com.example.foody.search.presentation_mvvm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.foody.domain.model.RecipeInfo
import com.example.foody.search.presentation_mvvm.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
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

        val state by viewModel.state.collectAsState()
        LazyColumn() {
            items(
                count = state.recipes.size,
                key = { index -> state.recipes[index].id } // ???
            ) {
                RecipeItem(item = state.recipes[it]) // ???
            }
        }
    }
}

@Composable
fun RecipeItem(item: RecipeInfo) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.size(16.dp))
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
        )

    }
}