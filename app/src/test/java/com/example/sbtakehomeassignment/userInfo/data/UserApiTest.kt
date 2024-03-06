package com.example.sbtakehomeassignment.userInfo.data

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
import org.mockito.Mock
import retrofit2.Response
import retrofit2.Retrofit
/**
 * This class tests the UserApi interface, which defines the network calls for fetching user information and repositories. It verifies that the API correctly handles responses from the server, including successful data retrieval and handling empty responses.
 *
 * Functions:
 * setup()
 * Purpose: Initializes the testing environment by setting up a MockWebServer and configuring Retrofit with a JSON converter to create an instance of UserApi.
 * How It Works:
 * Starts a MockWebServer.
 * Configures Retrofit to interact with the mock server.
 * Initializes UserApi with Retrofit.
 *
 * getUserInfo_EmptyResponse_true_Expected()
 * Purpose: Tests fetching user information with a non-empty response.
 * How It Works:
 * Enqueues a mock response with HTTP status 200 and a sample JSON body.
 * Calls getUserInfo on UserApi and asserts the response contains valid data.
 *
 * getUserReposList_EmptyResponse_true_Expected()
 * Purpose: Tests fetching user repositories with an empty response.
 * How It Works:
 * Enqueues a mock response with HTTP status 200 and an empty array body.
 * Calls getUserRepos on UserApi and asserts the response is empty.
 *
 *
 * getUserReposList_WithResponse_true_Expected()
 * Purpose: Tests fetching user repositories with a non-empty response.
 * How It Works:
 * Enqueues a mock response with HTTP status 200 and a sample JSON body containing repositories.
 * Calls getUserRepos on UserApi and asserts the response is not empty.
 * */
class UserApiTest {

    private lateinit var mockWebServer: MockWebServer


    private lateinit var userApi: UserApi

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
    }

    @Test
    fun getUserInfo_EmptyResponse_true_Expected() = runTest {
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_info_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)
        val response = userApi.getUserInfo("octocat")
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response.id!=0)
    }

    @Test
    fun getUserReposList_EmptyResponse_true_Expected() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)
        val response = userApi.getUserRepos("octocat")
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response.isEmpty())
    }

    @Test
    fun getUserReposList_WithResponse_true_Expected() = runTest {
        val mockResponse = MockResponse()
        val testResponse = Helper.readFileResource("/user_repos_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(testResponse)
        mockWebServer.enqueue(mockResponse)
        val response = userApi.getUserRepos("octocat")
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response.isNotEmpty())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}