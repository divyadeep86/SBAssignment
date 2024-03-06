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
import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Test

import retrofit2.Retrofit

/**
 * Purpose: This class is designed to test the UserInfoUseCase, which is responsible for fetching user information. It verifies that the use case correctly handles both successful responses and errors from the network layer, represented by a MockWebServer.
 *
 * Functions:
 * setUp()
 * Purpose: Prepares the testing environment by initializing the MockWebServer, setting up Retrofit with a JSON converter, and creating instances of UserApi, UserRepository, and UserInfoUseCase.
 * How It Works:
 * Initializes MockWebServer for mocking API calls.
 * Configures Retrofit with a custom JSON setup to interact with the mock server.
 * Initializes UserRepository with the mocked UserApi.
 * Creates an instance of UserInfoUseCase with the repository.
 *
 * getUserInfo_True_Expected()
 * Purpose: Tests the use case's ability to handle successful API responses.
 * How It Works:
 * Enqueues a mock response with HTTP status 200 and a sample JSON body to simulate a successful API call.
 * Executes the use case to fetch user info and collects the resulting state changes.
 * Asserts that the initial state indicates loading and the subsequent state contains the expected data.
 *
 *
 * getUserInfo_WithError_True_Expected()
 * Purpose: Tests the use case's error handling capabilities when the API call fails.
 * How It Works:
 * Enqueues a mock response with HTTP status 404 to simulate an API error.
 * Executes the use case to fetch user info and collects the resulting state changes.
 * Asserts that the initial state indicates loading and the subsequent state contains an error message, verifying proper error handling.
 *
 * tearDown()
 * Purpose: Cleans up after tests by shutting down the MockWebServer.
 * How It Works: Calls shutdown() on MockWebServer to release resources and stop serving mock responses.
 * */

class UserInfoUseCaseTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var userApi: UserApi
    private lateinit var userRepository: UserRepository
    private lateinit var userInfoUseCase: UserInfoUseCase


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
        userInfoUseCase = UserInfoUseCase(userRepository = userRepository)


    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun getUserInfo_True_Expected() = runTest {
        // Prepare and enqueue a successful mock response
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_info_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute use case and collect results
        val dataState = userInfoUseCase.getUserInfo("octocat").toList()

        // Assert initial loading state is true
        Assert.assertEquals(true, dataState[0].isLoading)
        // Assert data is successfully received
        Assert.assertEquals(true, dataState[1].data != null)
    }

    @Test
    fun getUserInfo_WithError_True_Expected() = runTest {
        // Prepare and enqueue a mock response simulating an error
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        // Execute use case and collect results
        val dataState = userInfoUseCase.getUserInfo("octocat").toList()

        // Assert initial loading state is true
        Assert.assertEquals(true, dataState[0].isLoading)
        // Assert error message is received
        Assert.assertEquals(true, dataState[1].errorMessage != null)
    }
}