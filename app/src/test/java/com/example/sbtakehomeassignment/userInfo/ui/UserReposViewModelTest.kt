package com.example.sbtakehomeassignment.userInfo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.sbtakehomeassignment.common.states.RequestProgressStatus
import com.example.sbtakehomeassignment.common.utils.Helper
import com.example.sbtakehomeassignment.userInfo.data.UserApi
import com.example.sbtakehomeassignment.userInfo.data.UserRepository
import com.example.sbtakehomeassignment.userInfo.domain.GetUserReposUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher

import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import retrofit2.Retrofit
/**
 * Purpose: This class is designed for unit testing the UserReposViewModel. It aims to verify the ViewModel's behavior in different scenarios, such as successful data loading and error handling. The tests ensure that the ViewModel updates its state correctly based on the responses from a mocked web server.
 *
 *
 * Functions:
 * setUp()
 * Purpose: Prepares the testing environment before each test case runs. It initializes necessary components like MockWebServer, UserApi, UserRepository, GetUserReposUseCase, and UserReposViewModel.
 * How It Works:
 * Sets a main dispatcher for coroutines to a test dispatcher.
 * Initializes MockWebServer for mocking API calls.
 * Creates a Retrofit instance with a JSON converter and sets it to use the mock server's URL.
 * Initializes UserRepository with the mocked UserApi.
 * Initializes GetUserReposUseCase with the UserRepository.
 * Initializes UserReposViewModel with a SavedStateHandle and the use case.
 *
 *
 * checkViewModelStateTrue_expected()
 * Purpose: Tests the ViewModel's state update when the API call is successful.
 * How It Works:
 * Enqueues a mock response with HTTP status 200 and a predefined JSON body to simulate a successful API response.
 * Triggers the use case to fetch user repositories and observes the changes in ViewModel's state.
 * Asserts that the loading state and request progress status are updated correctly in the ViewModel.
 *
 * checkErrorState_True_expected()
 * Purpose: Tests the ViewModel's error handling when the API call fails.
 * How It Works:
 * Enqueues a mock response with HTTP status 404 to simulate an API error.
 * Triggers the use case to fetch user repositories and observes the changes in ViewModel's state.
 * Asserts that the ViewModel correctly updates its error message and loading state
 *
 * tearDown()
 * Purpose: Cleans up resources after each test case is run.
 * How It Works:
 * Shuts down the MockWebServer.
 * Resets the main dispatcher to its original state.
 * */
class UserReposViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var getUserReposUseCase: GetUserReposUseCase
    private lateinit var userReposViewModel: UserReposViewModel
    private lateinit var userApi: UserApi
    private lateinit var userRepository: UserRepository
    private lateinit var savedStateHandle: SavedStateHandle


    @OptIn(ExperimentalSerializationApi::class, ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
           Dispatchers.setMain(StandardTestDispatcher())
        mockWebServer = MockWebServer()
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
        }
        val contentType = "application/json".toMediaType()
        userApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType)).build()
            .create(UserApi::class.java)
        userRepository = UserRepository(userApi = userApi)
        getUserReposUseCase = GetUserReposUseCase(userRepository = userRepository)
        savedStateHandle = SavedStateHandle()
        userReposViewModel = UserReposViewModel(savedStateHandle, getUserReposUseCase)

    }


    @Test
    fun checkViewModelStateTrue_expected() = runTest {
    // Prepare mock response with HTTP 200 status and body from a JSON file
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_repos_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)// Enqueue the prepared mock response

        // Fetch user repositories and collect the resulting state changes into a list
        val dataState = getUserReposUseCase.getUserRepos("octocat").toList()

        // First state: Loading
        dataState[0].run {
            userReposViewModel.updateState(
                isLoading = dataState[0].isLoading,// Update ViewModel's loading state
                requestProgressStatus = dataState[0].requestProgressStatus// Update ViewModel's progress status
            )
            // Assert loading state is true and progress status is InProgress
            Assert.assertEquals(true, userReposViewModel.viewState.value.isLoading)
            Assert.assertEquals(
                true,
                userReposViewModel.viewState.value.requestProgressStatus == RequestProgressStatus.InProgress
            )
        }
        // Second state: Data Loaded
        dataState[1].run {
            userReposViewModel.updateState(
                dataState = dataState[1].data,// Update ViewModel with fetched data
                requestProgressStatus = dataState[1].requestProgressStatus// Update ViewModel's progress status to Completed
            )
            // Assert data is loaded and not empty, and progress status is Completed
            Assert.assertEquals(true, userReposViewModel.viewState.value.dataState != null && userReposViewModel.viewState.value.dataState?.isNotEmpty()==true)
            Assert.assertEquals(
                true,
                userReposViewModel.viewState.value.requestProgressStatus == RequestProgressStatus.Completed
            )
        }

    }

    @Test
    fun checkErrorState_True_expected() = runTest {
        // Prepare mock response with HTTP 404 status to simulate an error and set body from a JSON file
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_repos_response.json")
        mockResponse.setResponseCode(404)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse) // Enqueue the prepared mock response

        // Fetch user repositories and collect the resulting state changes into a list
        val dataState = getUserReposUseCase.getUserRepos("octocat").toList()

        // First state: Loading with no error message yet
        dataState[0].run {
            userReposViewModel.updateState(
                isLoading = dataState[0].isLoading, // Update ViewModel's loading state
                errorMessage = dataState[0].errorMessage, // Initially, there's no error message
                requestProgressStatus = dataState[0].requestProgressStatus // Update ViewModel's progress status
            )
            // Assert loading state is true, error message is null, and progress status is InProgress
            Assert.assertEquals(true, userReposViewModel.viewState.value.isLoading)
            Assert.assertEquals(true, userReposViewModel.viewState.value.errorMessage == null)
            Assert.assertEquals(
                true,
                userReposViewModel.viewState.value.requestProgressStatus == RequestProgressStatus.InProgress
            )
        }

        // Second state: Error occurred
        dataState[1].run {
            userReposViewModel.updateState(errorMessage = dataState[1].errorMessage) // Update ViewModel with the error message
            // Assert that there is now an error message
            Assert.assertEquals(true, userReposViewModel.viewState.value.errorMessage != null)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }
}
