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

class SearchViewModelTest {
    
    @OptIn(ExperimentalCoroutinesApi::class)
    private var dispatcher = UnconfinedTestDispatcher()
    
    private lateinit var subject: SearchViewModel
    private lateinit var repoMock: RecipesSearchRepository
    private lateinit var fakeState: RecipeListState
    
    private val testSearchTerm = "pie"
    private val testListOfRecipes = getRecipeListTestData(5)
    
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repoMock = mockk()
    }
    
    private fun createViewModel() {
        subject = SearchViewModel(repoMock)
    }
    
    @Test
    fun `Given we have a valid searchTerm, then list of recipes is fetched`() {
        // Arrangement
        coEvery { repoMock.search(testSearchTerm) } returns testListOfRecipes
        createViewModel()
        
        // Action
        subject.updateSearchTerm(testSearchTerm)
        
        // Assertion
        // Testing that searchTerm is set to input value
        assertEquals(testSearchTerm, subject.state.value.searchBarState.searchTerm)
        
        // Action
        subject.search()
        
        // Assertions
        fakeState = RecipeListState.Success(testListOfRecipes)
        coVerify { repoMock.search(testSearchTerm) }
        assertEquals(fakeState, subject.state.value.recipeListState)
        
        // testing that searchTerm is empty
        assertEquals("", subject.state.value.searchBarState.searchTerm)
        // testing that the searchBar is hidden
        assertEquals(false, subject.state.value.searchBarState.expandedState)
    }
    
    @Test
    fun `Given I landed on a searchScreen, Then the input field is empty, And search bar is visible, And recipe list starts empty`() {
        // Arrangement
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        val testScope = TestScope()
    
        // Assertions
        testScope.runTest {
            coEvery { repoMock.search(testSearchTerm) } returns testListOfRecipes
//            coEvery { repoMock.search("") } returns testListOfRecipes
            coEvery { repoMock.randomRecipe() }.coAnswers { testListOfRecipes }
            
            createViewModel()

            lateinit var turbine: ReceiveTurbine<SearchScreenState>
            turbineScope {
                
                turbine = subject.state.testIn(testScope)
                coEvery { repoMock.search(testSearchTerm) } returns testListOfRecipes
            }
            
            // Testing state cycle change
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
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    
//    @Test
//    fun showRandomRecipe() {
//    }

}