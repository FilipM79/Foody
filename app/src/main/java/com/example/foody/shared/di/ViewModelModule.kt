package com.example.foody.shared.di

import com.example.foody.shared.data.FoodRecipesApiService
import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.search.domain.FoodRecipesSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

// 9-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6. Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// This is the module for search viewModel. What do we do here?
@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideFoodRecipesSearchRepository(
        apiService: FoodRecipesApiService
    ): FoodRecipesSearchRepository = apiService

    @Provides
    @ViewModelScoped
    fun provideRecipeDetailsSearchRepository(
        apiService: FoodRecipesApiService
    ): RecipeDetailsSearchRepository = apiService
}