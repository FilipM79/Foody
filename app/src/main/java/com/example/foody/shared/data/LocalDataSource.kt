package com.example.foody.shared.data

import com.example.foody.shared.data.database.RecipesDao
import com.example.foody.shared.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// this is a source for local Room database
class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}