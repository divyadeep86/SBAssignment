package com.example.sbtakehomeassignment.userInfo.domain

import com.example.sbtakehomeassignment.common.utils.Helper
import com.example.sbtakehomeassignment.userInfo.data.UserApi
import com.example.sbtakehomeassignment.userInfo.data.UserRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
/**
 * Purpose: This class tests the GetUserReposUseCase, which is responsible for fetching a user's repositories. It aims to validate the use case's functionality in handling both successful responses and errors from the network layer, using a MockWebServer to simulate API responses.
 *
 * Functions:
 * setUp()
 * Purpose: Sets up the testing environment by initializing the MockWebServer, configuring Retrofit with a JSON converter, and creating instances of UserApi, UserRepository, and GetUserReposUseCase.
 * How It Works:
 * Initializes MockWebServer for mocking API calls.
 * Configures Retrofit with a custom JSON setup to interact with the mock server.
 * Initializes UserRepository with the mocked UserApi.
 * Creates an instance of GetUserReposUseCase with the repository.
 *
 * getUserRepos_True_Expected()
 * Purpose: Tests the use case's ability to handle successful API responses when fetching user repositories.
 * How It Works:
 * Enqueues a mock response with HTTP status 200 and a sample JSON body to simulate a successful API call.
 * Executes the use case to fetch user repositories and collects the resulting state changes.
 * Asserts that the initial state indicates loading and the subsequent state contains the expected data, verifying successful data retrieval.
 *
 * getUserInfo_WithError_True_Expected()
 * Purpose: Tests the use case's error handling capabilities when the API call to fetch user repositories fails.
 * How It Works:
 * Enqueues a mock response with HTTP status 404 to simulate an API error.
 * Executes the use case to fetch user repositories and collects the resulting state changes.
 * Asserts that the initial state indicates loading and the subsequent state contains an error message, ensuring proper error handling.
 *
 * tearDown()
 * Purpose: Cleans up after tests by shutting down the MockWebServer.
 * How It Works: Calls shutdown() on MockWebServer to release resources and stop serving mock responses.
 * */
class GetUserReposUseCaseTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var userApi: UserApi
    private lateinit var userRepository: UserRepository
    private lateinit var getUserReposUseCase: GetUserReposUseCase

    @OptIn(ExperimentalSerializationApi::class)
    @Before
    fun setUp() {
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
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getUserRepos_True_Expected() = runTest {
        // Prepare and enqueue a successful mock response
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_repos_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute use case and collect results
        val dataState = getUserReposUseCase.getUserRepos("octocat").toList()

        // Assert initial loading state is true
        assertEquals(true, dataState[0].isLoading)
        // Assert data is successfully received and loading is false
        assertEquals(true, dataState[1].data?.isNotEmpty() == true && !dataState[1].isLoading)
    }

    @Test
    fun getUserInfo_WithError_True_Expected() = runTest {
        // Prepare and enqueue a mock response simulating an error
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        // Execute use case and collect results
        val dataState = getUserReposUseCase.getUserRepos("octocat").toList()

        // Assert initial loading state is true
        assertEquals(true, dataState[0].isLoading)
        // Assert error message is received
        assertEquals(true, dataState[1].errorMessage != null)
    }
}