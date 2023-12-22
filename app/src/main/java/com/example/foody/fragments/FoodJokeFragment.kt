package com.example.foody.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.foody.R
import com.example.foody.recipe_details.presentation.ui.RecipeDetailsScreen
import com.example.foody.search.presentation_mvvm.ui.SearchScreen

class FoodJokeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                RecipeDetailsScreen()
            }
        }
    }

}