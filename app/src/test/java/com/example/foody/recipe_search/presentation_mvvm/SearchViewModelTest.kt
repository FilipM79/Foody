package com.example.foody.recipe_search.presentation_mvvm

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.turbineScope
import com.example.foody.recipe_search.domain.RecipesSearchRepository
import com.example.foody.recipe_search.presentation_mvvm.model.RecipeListState
import com.example.foody.recipe_search.presentation_mvvm.model.SearchScreenState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SearchViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private var dispatcher = UnconfinedTestDispatcher()
    
    private lateinit var subject: SearchViewModel
    private lateinit var repoMock: RecipesSearchRepository
    private lateinit var testSearchTerm: String
    private val testListOfRecipes = getRecipeListTestData(5)
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repoMock = mockk()
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `Given we have a valid search term, Then list of recipes is fetched`() {
        // Arrangement
        testSearchTerm = "pie"
        coEvery { repoMock.search(testSearchTerm) } returns testListOfRecipes
        createViewModel()
        
        // Action
        subject.updateSearchTerm(testSearchTerm)
        
        // Assertion, Testing that searchTerm is set to input value
        assertEquals(testSearchTerm, subject.state.value.searchBarState.searchTerm)
        
        // Action
        subject.search()
        
        // Assertions
        // Testing that after calling a search function with valid searchTerm, the state is Success
        coVerify { repoMock.search(testSearchTerm) }
        assertEquals(RecipeListState.Success(testListOfRecipes), subject.state.value.recipeListState)
        
        // testing that searchTerm is set to empty after the search
        assertEquals("", subject.state.value.searchBarState.searchTerm)
        // testing that the searchBar is hidden
        assertEquals(false, subject.state.value.searchBarState.expandedState)
    }
    
    @Test
    fun `Given we have an empty search term (or no results), Then the state is Empty`() {
        // Arrangement
        testSearchTerm = ""
        coEvery { repoMock.search(testSearchTerm) } returns emptyList()
        createViewModel()
    
        // Action
        subject.updateSearchTerm(testSearchTerm)
        subject.search()
    
        // Assertions
        coVerify { repoMock.search(testSearchTerm) }
        assertEquals(RecipeListState.Empty, subject.state.value.recipeListState)
    
        // testing that searchTerm is set to empty after the search
        assertEquals("", subject.state.value.searchBarState.searchTerm)
        // testing that the searchBar is visible (expanded)
        assertEquals(true, subject.state.value.searchBarState.expandedState)
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given we landed on search screen, Then search bar is expanded and empty, And random recipe is auto generated`() {
        // Arrangement
        testSearchTerm = "pie"
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        
        val testScope = TestScope()
        lateinit var turbine: ReceiveTurbine<SearchScreenState>
        coEvery { repoMock.randomRecipe() }.coAnswers { testListOfRecipes }
        
        testScope.runTest {
            // Action
            createViewModel()

            turbineScope { turbine = subject.state.testIn(testScope) }
    
            // Assertions
            // Testing random recipe state cycle proper change on viewModel initialisation
            assertEquals(RecipeListState.Idle, turbine.awaitItem().recipeListState)
            assertEquals(RecipeListState.Loading, turbine.awaitItem().recipeListState)
            assertEquals(RecipeListState.Success(testListOfRecipes), turbine.awaitItem().recipeListState)
            turbine.ensureAllEventsConsumed()
            turbine.cancel()

            // testing that searchTerm is empty
            assertEquals("", subject.state.value.searchBarState.searchTerm)
            // testing that the searchBar is visible
            assertEquals(true, subject.state.value.searchBarState.expandedState)
        }
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given we landed on search screen, Then on button-click, a random recipe is re-generated`() {
        // Arrangement
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        
        val testScope = TestScope()
        lateinit var turbine: ReceiveTurbine<SearchScreenState>
    
        coEvery { repoMock.randomRecipe() }.coAnswers { testListOfRecipes }
        
        testScope.runTest {
            createViewModel()
            
            turbineScope {
                turbine = subject.state.testIn(testScope)
                coEvery { repoMock.randomRecipe() } returns testListOfRecipes
            }
            turbine.skipItems(3)
            turbine.ensureAllEventsConsumed()
    
            // Action (on "generate" button click)
            subject.showRandomRecipe()
    
            // Assertions
            // Testing the state after clicking on "generate random recipe" button
            assertEquals(RecipeListState.Loading, turbine.awaitItem().recipeListState)
            assertEquals(RecipeListState.Success(testListOfRecipes), turbine.awaitItem().recipeListState)
            turbine.ensureAllEventsConsumed()
            turbine.cancel()
        }
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test Given we land on search screen, On recipe list fail to fetch, Then error message is shown`() {
        // Arrangement
        testSearchTerm = "some term"
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        
        coEvery { repoMock.search(testSearchTerm) } throws IOException()
        createViewModel()
        
        // Action
        subject.search()
        
        // Assertion
        runTest{
            delay(500)
//            assertEquals(RecipeListState.Error("Unknown error from SearchVM."), subject.state.value.recipeListState)
            assertEquals(RecipeListState.Loading, subject.state.value.recipeListState)
        }
        assertEquals(RecipeListState.Error("Unknown error from SearchVM."), subject.state.value.recipeListState)
    }
    
    private fun createViewModel() {
        subject = SearchViewModel(repoMock)
    }
}