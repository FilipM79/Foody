package com.example.foody.recipe_details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.foody.recipe_details.presentation.ui.RecipeDetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {
    private val args by navArgs<RecipeDetailsFragmentArgs>()

    private val viewModel by viewModels<RecipeDetailsViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<RecipeDetailsViewModelFactory> { factory ->
                    factory.create(args.recipeId)
                }
        }
    )

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