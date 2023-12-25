package com.example.foody.recipe_details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.example.foody.recipe_details.presentation.ui.RecipeDetailsScreen
import com.example.foody.search.presentation_mvvm.ui.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

class RecipeDetailsFragment : Fragment() {

//    private var _binding: FragmentRecipesBinding? = null
//    private val binding: FragmentRecipesBinding get() = _binding!!

    val args by navArgs<RecipeDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                RecipeDetailsScreen(args.recipeId)
            }
        }
    }
}