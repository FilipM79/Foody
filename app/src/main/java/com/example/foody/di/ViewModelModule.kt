package com.example.foody.di

import com.example.foody.data.FoodRecipesApiService
import com.example.foody.search.domain.FoodRecipesSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideFoodRecipesSearchRepository(
        apiService: FoodRecipesApiService
    ): FoodRecipesSearchRepository = apiService
}