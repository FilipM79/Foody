package com.example.foody.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.example.foody.R

class RecipesFragment : Fragment() {

//    private var _binding: FragmentRecipesBinding? = null
//    private val binding: FragmentRecipesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)

        view.findViewById<ShimmerRecyclerView>(R.id.recyclerview).showShimmerAdapter()

        return view
    }

}