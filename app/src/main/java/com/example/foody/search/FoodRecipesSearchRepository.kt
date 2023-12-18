package com.example.foody.search

import android.util.Log
import com.example.foody.FoodRecipesApi
import okhttp3.internal.http2.ErrorCode
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject



class FoodRecipesSearchRepository @Inject constructor(retrofit: Retrofit) {

    companion object {
        private const val PAGE_SIZE: Int = 10
        private const val TAG = "FoodRecipesSearchRepository"
    }

    private val service = retrofit.create(FoodRecipesSearchApi::class.java)

    suspend fun search(searchTerm: String) : List<BasicRecipeInfo> {
        val response = service.search(
            searchTerm = searchTerm, pageSize = PAGE_SIZE, startingPosition = 0
        )
        if(response.isSuccessful) {
            return response.body()!!.mapToBasicInfoList()
        } else {
            handleError(response.code(), response.message())
            throw IOException("${response.code()}, ${response.message()}")
        }
    }

    private fun handleError(code: Int, errorMessage: String) {
        Log.e(TAG, "$code, $errorMessage")
    }

    private fun SearchResult.mapToBasicInfoList(): List<BasicRecipeInfo> = this.recipes.map {
        BasicRecipeInfo(
            id = it.id,
            title = it.title,
            imageUrl = it.imageUrl,
            imageType = it.imageType
            )
    }
}