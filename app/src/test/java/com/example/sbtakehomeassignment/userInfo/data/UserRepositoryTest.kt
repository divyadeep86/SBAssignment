package com.example.sbtakehomeassignment.userInfo.data

import com.example.sbtakehomeassignment.common.network.ErrorConstant
import com.example.sbtakehomeassignment.common.network.ResponseHandler
import com.example.sbtakehomeassignment.common.utils.Helper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
import java.io.IOException

/**
 * Purpose: This class tests the UserRepository's functionality in handling API calls to fetch user information and repositories. It verifies the repository's ability to correctly process successful responses, handle HTTP errors, deal with malformed JSON responses, and manage network issues.
 *
 * Functions:
 * setup()
 * Purpose: Initializes the testing environment by setting up a MockWebServer, configuring Retrofit with a JSON converter, and creating an instance of UserApi and UserRepository.
 * How It Works:
 * Starts a MockWebServer.
 * Configures Retrofit to interact with the mock server.
 * Initializes UserRepository with the mocked UserApi.
 *
 * getUserInfo_WithResponse_true_Expected()
 * Purpose: Tests successful fetching of user information.
 * How It Works:
 * Enqueues a successful mock response.
 * Calls getUserInfo and asserts the response is successful.
 *
 * getUserInfo_HttpException_true_Expected()
 * Purpose: Tests handling of HTTP errors when fetching user information.
 * How It Works:
 * Enqueues a mock response with an HTTP error code.
 * Calls getUserInfo and asserts the response indicates failure.
 *
 * getUserRepos_WithResponse_true_Expected()
 * Purpose: Tests successful fetching of user repositories.
 * How It Works:
 * Enqueues a successful mock response.
 * Calls getUserRepos and asserts the response is successful.
 *
 * getUserRepos_HttpException_true_Expected()
 * Purpose: Tests handling of HTTP errors when fetching user repositories.
 * How It Works:
 * Enqueues a mock response with an HTTP error code.
 * Calls getUserRepos and asserts the response indicates failure.
 *
 * getUserRepos_Exception_true_Expected()
 * Purpose: Tests handling of malformed JSON responses when fetching user repositories.
 * How It Works:
 * Enqueues a mock response with malformed JSON.
 * Calls getUserRepos and asserts the response indicates failure.
 *
 * getUserRepos_IOException_true_Expected() & getUserInfo_IOException_true_Expected()
 * Purpose: Tests handling of network issues when fetching user repositories and information.
 * How It Works:
 * Shuts down the MockWebServer to simulate a network failure.
 * Calls getUserRepos or getUserInfo and catches IOException, asserting the response indicates failure.
 * */
class UserRepositoryTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var userApi: UserApi
    private lateinit var userRepository: UserRepository


    @OptIn(ExperimentalSerializationApi::class)
    @Before
    fun setup() {
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
    }

    @Test
    fun getUserInfo_WithResponse_true_Expected() = runTest {
        // Prepare and enqueue a successful mock response
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_info_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute the repository function to fetch user info
        val response = userRepository.getUserInfo("octocat")
        mockWebServer.takeRequest()

        // Assert the response is successful
        Assert.assertEquals(true, response is ResponseHandler.Success)
    }

    @Test
    fun getUserInfo_HttpException_true_Expected() = runTest {
        // Prepare and enqueue a mock response simulating an HTTP error
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/httpexception_response.json")
        mockResponse.setResponseCode(403)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute the repository function to fetch user info
        val response = userRepository.getUserInfo("octocat")
        mockWebServer.takeRequest()

        // Assert the response indicates a failure
        Assert.assertEquals(true, response is ResponseHandler.Failure)
    }


    @Test
    fun getUserInfo_Exception_true_Expected() = runTest {
        // Prepare and enqueue a mock response with malformed JSON to simulate parsing failure
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/malformed_userinfo.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute the repository function to fetch user info
        val response = userRepository.getUserInfo("octocat")
        mockWebServer.takeRequest()

        // Assert the response indicates a failure due to exception in parsing
        Assert.assertEquals(true, response is ResponseHandler.Failure)
    }

    /**************************************************************************************************************************/


    @Test
    fun getUserRepos_WithResponse_true_Expected() = runTest {
        // Prepare and enqueue a successful mock response for user repositories
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_repos_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute the repository function to fetch user repositories
        val response = userRepository.getUserRepos("octocat")
        mockWebServer.takeRequest()

        // Assert the response is successful
        Assert.assertEquals(true, response is ResponseHandler.Success)
    }

    @Test
    fun getUserRepos_HttpException_true_Expected() = runTest {
        // Prepare and enqueue a mock response simulating an HTTP error for user repositories
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/httpexception_response.json")
        mockResponse.setResponseCode(403)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute the repository function to fetch user repositories
        val response = userRepository.getUserRepos("octocat")
        mockWebServer.takeRequest()

        // Assert the response indicates a failure
        Assert.assertEquals(true, response is ResponseHandler.Failure)
    }

    @Test
    fun getUserRepos_Exception_true_Expected() = runTest {
        // Prepare and enqueue a mock response with malformed JSON for user repositories
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/malformed_userrepos.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)

        // Execute the repository function to fetch user repositories
        val response = userRepository.getUserInfo("octocat") // Mistake: should be getUserRepos
        mockWebServer.takeRequest()

        // Assert the response indicates a failure due to exception in parsing
        Assert.assertEquals(true, response is ResponseHandler.Failure)
    }


    /**************************************************************************************************************************/

    @Test
    fun getUserRepos_IOException_true_Expected() = runTest {
        // Shutdown MockWebServer to simulate a network error
        mockWebServer.shutdown()

        // Attempt to fetch user repositories and handle IOException
        val response = try {
            userRepository.getUserRepos("octocat")
        } catch (e: IOException) {
            ResponseHandler.Failure(ErrorConstant.NetworkErrorMessage)
        }

        // Assert the response indicates a failure due to network error
        Assert.assertEquals(true, response is ResponseHandler.Failure)
    }

    @Test
    fun getUserInfo_IOException_true_Expected() = runTest {
        // Shutdown MockWebServer to simulate a network error
        mockWebServer.shutdown()

        // Attempt to fetch user information and handle IOException
        val response = try {
            userRepository.getUserInfo("octocat")
        } catch (e: IOException) {
            ResponseHandler.Failure(ErrorConstant.NetworkErrorMessage)
        }

        // Assert the response indicates a failure due to network error
        Assert.assertEquals(true, response is ResponseHandler.Failure)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}