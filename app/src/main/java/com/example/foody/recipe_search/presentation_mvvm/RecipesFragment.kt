package com.example.foody.recipe_search.presentation_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foody.recipe_search.presentation_mvvm.ui.SearchScreen

class RecipesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val goToDetailsScreen: (String) -> Unit = { recipeId ->
            findNavController().navigate(RecipesFragmentDirections.actionSearchToDetails(recipeId))
        }

        return ComposeView(requireContext()).apply {
            setContent {
                SearchScreen(goToDetailsScreen)
            }
        }
    }
}