package com.example.foody.shared.data

import android.util.Log
import com.example.foody.shared.domain.model.Ingredient
import com.example.foody.shared.domain.model.RecipeInfo
import com.example.foody.recipe_search.domain.RecipesSearchRepository
import com.example.foody.shared.domain.model.RecipeResult
import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.recipe_search.data.model.RecipesSearchResponse
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

// 8-th step
// (1.NetworkModule, 2.RecipeItemResponse, 3.RecipeSearchResponse 4.FoodRecipesApi,
// 5.FoodRecipesSearchRepository, 6.Ingredient, 7.RecipeInfo, 8.FoodRecipesApiService,
// 9.ViewModelModule, 10.SearchScreenState, 11.searchViewModel, 12.SearchScreen,
// 13.RecipesFragment, 14.MainActivity)

// retrofit in this class constructor is a retrofit from NetworkModule
// here we are making a Recipe object from an Api response, by mapping ...
@ViewModelScoped
class RecipesApiService @Inject constructor(retrofit: Retrofit)
    : RecipesSearchRepository, RecipeDetailsSearchRepository {

    companion object {
//        private const val PAGE_SIZE: Int = 10
        private const val TAG = "FoodRecipesSearchRepository"
    }

    // This is the Retrofit service, created from FoodRecipesApi interface
    // we are using a constructor passed parameter retrofit to invoke a create function on it.
    // This way we create a service which is of that interface type
    // We can then access and override functions from that interface, and get a response back
    private val service = retrofit.create(RecipesApi::class.java)

    // Here we override a function from FoodRecipesSearchRepository interface
    override suspend fun search(searchTerm: String) : List<RecipeInfo> {
        val response = service.search(searchTerm = searchTerm)
        if (response.isSuccessful) {
            return response.body()?.mapToInfoList()
                ?: throw NullPointerException("Search response body is null.")
        } else {
            handleError(response.code(), response.message())
            throw IOException("${response.code()}, ${response.message()}")
        }
    }

    private fun handleError(code: Int, errorMessage: String) {
        Log.e(TAG, "$code, $errorMessage")
    }

    // Making extension function for mapping response to recipe list
    private fun RecipesSearchResponse.mapToInfoList(): List<RecipeInfo>  = this.recipes?.map { it ->
        RecipeInfo(
            id = it.idMeal!!,
            title = it.strMeal!!,
            cuisine = it.strArea!!,
            category = it.strCategory!!,
            ingredients = it.mapToIngredients(),
            recipe = it.strInstructions!!,
            imageUrl = it.strMealThumb,
            videoUrl = it.strYoutube,
            tags = it.strTags?.split(",").orEmpty()
        )
    } ?: emptyList()

    // Here we override a function from FoodRecipesSearchRepository interface
    override suspend fun getRecipeDetails(recipeId: String): RecipeInfo {
        val response = service.getRecipeDetails(recipeId)

        if (response.isSuccessful) {
            return response.body()?.let {
                it.mapToInfoList().firstOrNull()
                    ?: throw NullPointerException("Response is empty list.")
            } ?: throw NullPointerException("Response body is null.")
        } else {
            handleError(response.code(), response.message())
            throw IOException("${response.code()}, ${response.message()}")
        }
    }

    // We use this function when creating every of 20 ingredient fields
    private fun createIngredient(ingredient: String?, measure: String?): Ingredient? {
        return ingredient?.takeIf { it.isNotEmpty() }?.let { ingredient2 ->
            Ingredient(title = ingredient2, measure = measure.orEmpty()) // the same as measure ?: ""
        // The other way...
//        return if (ingredient.isNotEmpty()) { Ingredient(ingredient, measure) } else null
        }
    }

    // Making extension function for mapping response to list of all ingredient fields
    private fun RecipeResult.mapToIngredients(): List<Ingredient> {
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