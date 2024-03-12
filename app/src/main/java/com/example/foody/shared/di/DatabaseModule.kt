package com.example.foody.shared.di

import android.content.Context
import androidx.room.Room
import com.example.foody.shared.data.database.RecipesDatabase
import com.example.foody.shared.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Module for Room Database to tell Hilt how to provide Room Database Builder and RecipesDao
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // for a Room Database builder
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    // for providing RecipesDao
    @Singleton
    @Provides
    fun  provideDao(database: RecipesDatabase) = database.recipesDao()
}