package com.example.foody.shared.di

import com.example.foody.shared.data.RecipesApiService
import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.recipe_search.domain.RecipesSearchRepository
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

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideFoodRecipesSearchRepository(
        apiService: RecipesApiService
    ): RecipesSearchRepository = apiService

    @Provides
    @ViewModelScoped
    fun provideRecipeDetailsSearchRepository(
        apiService: RecipesApiService
    ): RecipeDetailsSearchRepository = apiService
}