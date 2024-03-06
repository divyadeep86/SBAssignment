package com.example.sbtakehomeassignment.userInfo.ui


import androidx.lifecycle.SavedStateHandle
import com.example.sbtakehomeassignment.common.states.RequestProgressStatus
import com.example.sbtakehomeassignment.common.utils.Helper
import com.example.sbtakehomeassignment.userInfo.data.UserApi
import com.example.sbtakehomeassignment.userInfo.data.UserRepository
import com.example.sbtakehomeassignment.userInfo.domain.UserInfoUseCase
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
 * Purpose: This class tests the UserInfoViewModel to ensure it correctly handles both successful and error responses when fetching user information. It aims to validate that the ViewModel updates its state appropriately in response to data fetched from a network request simulated by a MockWebServer.
 *
 * Functions:
 * setUp()
 * Purpose: Initializes necessary components before each test case. It sets up a mock server, Retrofit with a JSON converter for the API, a repository, a use case, and the ViewModel itself.
 * How It Works:
 * Initializes MockWebServer.
 * Sets up Retrofit with a custom JSON configuration to interact with the mock server.
 * Creates instances of UserRepository, UserInfoUseCase, and UserInfoViewModel with the necessary dependencies.
 *
 * checkViewModelStateTrue_expected()
 * Purpose: Tests the ViewModel's behavior when receiving a successful response from the network request.
 * How It Works:
 * Enqueues a mock response with HTTP status 200 and a sample JSON body.
 * Executes the use case to fetch user info and collects the results.
 * Verifies that the ViewModel correctly updates its loading state and request progress status initially to InProgress and then to Completed upon successful data retrieval.
 *
 * checkErrorState_True_expected()
 * Purpose: Tests the ViewModel's error handling capabilities when the network request fails.
 * How It Works:
 * Enqueues a mock response with HTTP status 404 to simulate an error.
 * Executes the use case to fetch user info and collects the results.
 * Checks that the ViewModel updates its loading state and sets an error message appropriately, indicating robust error handling.
 *
 * tearDown()
 * Purpose: Cleans up resources after each test case, ensuring a fresh environment for subsequent tests.
 * How It Works:
 * Shuts down the MockWebServer to release any held resources and stop serving mock responses.
 * */
class UserInfoViewModelTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var userInfoUseCase: UserInfoUseCase
    private lateinit var userInfoViewModel: UserInfoViewModel
    private lateinit var userApi: UserApi
    private lateinit var userRepository: UserRepository
    private lateinit var savedStateHandle: SavedStateHandle

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
        savedStateHandle = SavedStateHandle()
        userInfoViewModel = UserInfoViewModel(savedStateHandle, userInfoUseCase)

    }

    @Test
    fun checkViewModelStateTrue_expected() = runTest {
        // Prepare and enqueue a successful mock response
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_info_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Fetch user info and process the state changes
        val dataState = userInfoUseCase.getUserInfo("octocat").toList()

        // First state: Loading
        dataState[0].run {
            userInfoViewModel.updateState(
                isLoading = dataState[0].isLoading, // Update loading state
                requestProgressStatus = dataState[0].requestProgressStatus // Update progress status to InProgress
            )
            // Assert loading is true and progress status is InProgress
            assertEquals(true, userInfoViewModel.viewState.value.isLoading)
            assertEquals(
                true,
                userInfoViewModel.viewState.value.requestProgressStatus == RequestProgressStatus.InProgress
            )
        }

        // Second state: Data Loaded
        dataState[1].run {
            userInfoViewModel.updateState(
                dataState = dataState[1].data, // Update ViewModel with fetched data
                requestProgressStatus = dataState[1].requestProgressStatus // Update progress status to Completed
            )
            // Assert data is loaded and progress status is Completed
            assertEquals(true, userInfoViewModel.viewState.value.dataState != null)
            assertEquals(
                true,
                userInfoViewModel.viewState.value.requestProgressStatus == RequestProgressStatus.Completed
            )
        }
    }

    @Test
    fun checkErrorState_True_expected() = runTest {
        // Prepare and enqueue a mock response simulating an error
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_info_response.json")
        mockResponse.setResponseCode(404)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Fetch user info and process the state changes
        val dataState = userInfoUseCase.getUserInfo("octocat").toList()

        // First state: Loading with no error message
        dataState[0].run {
            userInfoViewModel.updateState(
                isLoading = dataState[0].isLoading, // Update loading state
                errorMessage = dataState[0].errorMessage, // Initially, no error message
                requestProgressStatus = dataState[0].requestProgressStatus // Update progress status to InProgress
            )
            // Assert loading is true, no error message yet, and progress status is InProgress
            assertEquals(true, userInfoViewModel.viewState.value.isLoading)
            assertEquals(true, userInfoViewModel.viewState.value.errorMessage == null)
            assertEquals(
                true,
                userInfoViewModel.viewState.value.requestProgressStatus == RequestProgressStatus.InProgress
            )
        }

        // Second state: Error occurred
        dataState[1].run {
            userInfoViewModel.updateState(errorMessage = dataState[1].errorMessage) // Update ViewModel with the error message
            // Assert that an error message is now present
            assertEquals(true, userInfoViewModel.viewState.value.errorMessage != null)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}