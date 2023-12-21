package com.example.foody.data

import android.util.Log
import com.example.foody.domain.model.Ingredient
import com.example.foody.domain.model.RecipeInfo
import com.example.foody.search.domain.FoodRecipesSearchRepository
import com.example.foody.domain.model.RecipeItemResponse
import com.example.foody.search.data.model.RecipeSearchResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

// This is step 8
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6. Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// Retrofit in constructor is a retrofit from NetworkModule
@ViewModelScoped
class FoodRecipesApiService @Inject constructor(retrofit: Retrofit)
    : FoodRecipesSearchRepository {

    companion object {
        private const val PAGE_SIZE: Int = 10
        private const val TAG = "FoodRecipesSearchRepository"
    }

    // Retrofit service
    private val service = retrofit.create(FoodRecipesApi::class.java)

    override suspend fun search(searchTerm: String) : List<RecipeInfo> {
        val response = service.search(
            searchTerm = searchTerm
        )
        if (response.isSuccessful) {
            return response.body()?.mapToBasicInfoList()
                ?: throw NullPointerException("Response body is null.")
        } else {
            handleError(response.code(), response.message())
            throw IOException("${response.code()}, ${response.message()}")
        }
    }

    private fun handleError(code: Int, errorMessage: String) {
        Log.e(TAG, "$code, $errorMessage")
    }

    private fun RecipeSearchResponse.mapToBasicInfoList(): List<RecipeInfo> = this.recipes.map { it ->
        RecipeInfo(
            id = it.idMeal!!,
            title = it.strMeal!!,
            cuisine = it.strArea!!,
            category = it.strCategory!!,
            ingredients = it.mapToIngredients(),
            recipe = it.strInstructions!!,
            imageUrl = it.strMealThumb!!,
            videoUrl = it.strYoutube,
//            dateModified = it.dateModified.orEmpty(),
            tags = it?.strTags?.split(",").orEmpty()
        )
    }

    private fun createIngredient(
        ingredient: String?,
        measure: String?
    ): Ingredient? {

        // A moze i ovako
//        return if (ingredient.isNotEmpty()) {
//            Ingredient(title = ingredient, measure = measure)
//        } else null

        return ingredient?.takeIf { it.isNotEmpty() }?.let { ingredient2 ->
            Ingredient(title = ingredient2, measure = measure.orEmpty()) // the same as measure ?: ""
        }
    }

//    // Ovo je anonimna implementacija interfejsa
//    private val impl = object : FoodRecipesSearchApi {
//        override suspend fun search(searchTerm: String): Response<MealSearchResult> {
//
//        }
//    }

    private fun RecipeItemResponse.mapToIngredients(): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()

        createIngredient(strIngredient1, strMeasure1)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient2, strMeasure2)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient3, strMeasure3)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient4, strMeasure4)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient5, strMeasure5)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient6, strMeasure6)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient7, strMeasure7)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient8, strMeasure8)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient9, strMeasure9)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient10, strMeasure10)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient11, strMeasure11)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient12, strMeasure12)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient13, strMeasure13)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient14, strMeasure14)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient15, strMeasure15)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient16, strMeasure16)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient17, strMeasure17)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient18, strMeasure18)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient19, strMeasure19)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        createIngredient(strIngredient20, strMeasure20)?.let { ingredient ->
            ingredients.add(ingredient)
        }
        return ingredients
    }
}