package com.example.foody.recipe_details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.foody.recipe_details.presentation.ui.RecipeDetailsScreen
import com.example.foody.shared.presentation.assistedViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {
    private val args by navArgs<RecipeDetailsFragmentArgs>()

    private val viewModel: RecipeDetailsViewModel by assistedViewModels { args.recipeId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RecipeDetailsScreen(viewModel)
            }
        }
    }
}