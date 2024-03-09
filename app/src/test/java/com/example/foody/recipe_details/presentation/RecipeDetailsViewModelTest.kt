package com.example.foody.recipe_details.presentation

import com.example.foody.recipe_details.domain.RecipeDetailsSearchRepository
import com.example.foody.recipe_details.presentation.model.RecipeInfoState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailsViewModelTest {

    // For any test with coroutines (used in @Before and @After for setup/teardown before/after each test)
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var subject: RecipeDetailsViewModel

    // All dependencies of the subject are mocked (using mockk in this case, another alternative is Mockito library)
    private lateinit var repoMock: RecipeDetailsSearchRepository

    // Then we have some common test data set-up.
    private val testRecipeId = "recipe_id"

    @Before
    fun initialise() {
        Dispatchers.setMain(dispatcher)

        repoMock = mockk()
    }

    @Test
    fun `test Given we land on recipe detail screen, Then recipe details of that recipe are fetched`() {
        createViewModel()

        coVerify { repoMock.getRecipeDetails(testRecipeId) }
    }


    @Test
    fun `test Given we land on recipe detail screen, On a call to fetch recipe details, Then the loading is shown`() {
        createViewModel()

        assertEquals(RecipeInfoState.RecipeInfoLoading, subject.state.value.detailsState)
    }

    @Test
    fun `test Given we land on recipe detail screen, When recipe details are fetched, Then the recipe details are shown`() {
        val testRecipeInfo = getRecipeInfoTestData(testRecipeId)
        coEvery { repoMock.getRecipeDetails(testRecipeId) } returns testRecipeInfo

        createViewModel()

        assertEquals(RecipeInfoState.RecipeInfoLoading, subject.state.value.detailsState)

        coVerify { repoMock.getRecipeDetails(testRecipeId) }

        assertEquals(RecipeInfoState.RecipeInfoValue(testRecipeInfo), subject.state.value.detailsState)
    }

    @Test
    fun `test Given we land on recipe detail screen, On recipe details fail to fetch, When error message is null, Then the generic error is shown`() {
        coEvery { repoMock.getRecipeDetails(testRecipeId) } throws IOException()

        createViewModel()

        assertEquals(RecipeInfoState.RecipeInfoLoading, subject.state.value.detailsState)

        coVerify { repoMock.getRecipeDetails(testRecipeId) }

        assertEquals(RecipeInfoState.RecipeInfoError("Unknown error from RecipeDetailsVM"), subject.state.value.detailsState)
    }

    @Test
    fun `test Given we land on recipe detail screen, On recipe details fail to fetch, When error message is present, Then error message shown`() {
        val errorMessage = "error message"
        coEvery { repoMock.getRecipeDetails(testRecipeId) } throws IOException(errorMessage)

        createViewModel()

        assertEquals(RecipeInfoState.RecipeInfoLoading, subject.state.value.detailsState)

        coVerify { repoMock.getRecipeDetails(testRecipeId) }

        assertEquals(RecipeInfoState.RecipeInfoError(errorMessage), subject.state.value.detailsState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() {
        subject = RecipeDetailsViewModel(testRecipeId, repoMock)
    }
}
