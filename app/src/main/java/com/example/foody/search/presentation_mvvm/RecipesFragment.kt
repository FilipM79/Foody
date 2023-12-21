package com.example.foody.search.presentation_mvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.foody.search.presentation_mvvm.ui.SearchScreen

class RecipesFragment : Fragment() {

//    private var _binding: FragmentRecipesBinding? = null
//    private val binding: FragmentRecipesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                SearchScreen()
            }
        }
    }

}