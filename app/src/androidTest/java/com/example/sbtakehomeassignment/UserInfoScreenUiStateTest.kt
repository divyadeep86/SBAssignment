package com.example.sbtakehomeassignment


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sbtakehomeassignment.common.states.RequestProgressStatus
import com.example.sbtakehomeassignment.common.states.ViewState
import com.example.sbtakehomeassignment.common.uiComponents.LoadingContentWrapper
import com.example.sbtakehomeassignment.userInfo.domain.models.UserInfo
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userInfo.ui.UserInfoScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
/**
 * Purpose: This class is designed to test the UI states of the UserInfoScreen composable function within a Jetpack Compose application. It verifies different UI scenarios including the initial display, loading progress indication, error dialog presentation, and the successful display of user information and repositories.
 *
 * Setup
 * rule: An instance of createComposeRule(), providing a testing environment for composing and interacting with the UI elements defined in composable functions.
 * userInfoViewState and userReposViewState: Instances of ViewState<UserInfo> and ViewState<List<UserRepo>> respectively, used to simulate different states (loading, error, data) of user information and repositories.
 *
 * Test Functions
 * showUserInfoScreen_True_Expected()
 * Purpose: Verifies that the UserInfoScreen can be displayed with initial parameters.
 * How It Works: Sets the content of the test rule to display UserInfoScreen with default values and checks for its existence.
 *
 * showProgressOnUserInfoScreen_True_Expected()
 * Purpose: Tests the visibility of a progress indicator when the user info is being loaded.
 * How It Works: Sets userInfoViewState to a loading state and wraps UserInfoScreen within LoadingContentWrapper to handle loading state. Asserts that the progress indicator is visible.
 *
 * showErrorDialogUserInfoScreen_True_Expected()
 * Purpose: Ensures that an error dialog is shown when there's an error fetching user info.
 * How It Works: Configures userInfoViewState with an error message and checks if LoadingContentWrapper correctly triggers the display of an error dialog.
 *
 * showUserInfoAndRepoList_True_Expected()
 * Purpose: Confirms that user information and repository list are correctly displayed when data is available.
 * How It Works: Prepares userInfoViewState and userReposViewState with mock data indicating successful data retrieval. Verifies that both user information and repository list are present in the UI.
 *
 * Key Components
 * ViewState: A generic class used to represent different UI states (loading, error, success) along with associated data.
 * UserInfoScreen: The main composable function under test, responsible for displaying user information and repositories.
 * LoadingContentWrapper: A utility composable that wraps another composable to handle loading and error states, showing appropriate UI elements based on the state.
 * Testags: A utility object or class containing constants for test tags used to identify specific UI elements during testing.
 *
 * Conclusion
 * UserInfoScreenUiStateTest effectively demonstrates how to structure UI tests for a Jetpack Compose screen, covering various UI states and interactions. By manipulating the view states and asserting the presence or absence of key UI elements, it ensures that UserInfoScreen behaves as expected across different scenarios.
 *
 * **/
@RunWith(AndroidJUnit4::class)
class UserInfoScreenUiStateTest {
    @get:Rule
    val rule = createComposeRule()
    private lateinit var userInfoViewState: ViewState<UserInfo>
    private lateinit var userReposViewState: ViewState<List<UserRepo>>

    // Test to verify the UserInfoScreen is displayed correctly
    @Test
    fun showUserInfoScreen_True_Expected() {
        rule.setContent {
            // Set UserInfoScreen with initial data
            UserInfoScreen(userInfo = UserInfo(),
                userRepos = listOf(),
                searchQuery = "octocat",
                onSearchQueryChange = {},
                getUserInfo = { },
                navToUserDetails = {})
        }
    }

    // Test to verify loading state is shown on UserInfoScreen
    @Test
    fun showProgressOnUserInfoScreen_True_Expected() {
        rule.setContent {
            // Set loading state
            userInfoViewState = ViewState(isLoading = true)
            // Display UserInfoScreen within LoadingContentWrapper to handle loading state
            LoadingContentWrapper(isLoading = userInfoViewState.isLoading,
                errorMessage = userInfoViewState.errorMessage,
                showDialog = userInfoViewState.errorMessage != null,
                dismissDialog = {}) {
                UserInfoScreen(userInfo = UserInfo(),
                    userRepos = listOf(),
                    searchQuery = "octocat",
                    onSearchQueryChange = {},
                    getUserInfo = { },
                    navToUserDetails = {})
            }

        }
        // Assert loading indicator and UserInfoScreen are present
        rule.onNodeWithTag(Testags.UserInfoScreen).assertExists()
        rule.onNodeWithTag(Testags.ProgressIndicator).assertExists()
    }

    // Test to verify error dialog is shown on UserInfoScreen
    @Test
    fun showErrorDialogUserInfoScreen_True_Expected() {
        rule.setContent {
            // Set error message state
            userInfoViewState = ViewState(errorMessage = "Something wrong")
            // Display UserInfoScreen within LoadingContentWrapper to handle error state
            LoadingContentWrapper(isLoading = userInfoViewState.isLoading,
                errorMessage = userInfoViewState.errorMessage,
                showDialog = userInfoViewState.errorMessage != null,
                dismissDialog = {}) {
                UserInfoScreen(userInfo = UserInfo(),
                    userRepos = listOf(),
                    searchQuery = "octocat",
                    onSearchQueryChange = {},
                    getUserInfo = { },
                    navToUserDetails = {})
            }

        }
        // Assert error dialog and UserInfoScreen are present
        rule.onNodeWithTag(Testags.UserInfoScreen).assertExists()
        rule.onNodeWithTag(Testags.AlertDialog).assertExists()
    }
    // Test to verify user info and repository list are shown on UserInfoScreen
    @Test
    fun showUserInfoAndRepoList_True_Expected() {
        rule.setContent {
            // Set state with mock data for user info and repositories
            userInfoViewState = ViewState(
                dataState = UserInfo.getMockData(),
                requestProgressStatus = RequestProgressStatus.Completed
            )
            userReposViewState = ViewState(dataState = listOf(UserRepo.getMockData()))
            // Display UserInfoScreen within LoadingContentWrapper
            LoadingContentWrapper(isLoading = userInfoViewState.isLoading,
                errorMessage = userInfoViewState.errorMessage,
                showDialog = userInfoViewState.errorMessage != null,
                dismissDialog = {}) {
                UserInfoScreen(userInfo = userInfoViewState.dataState!!,
                    userRepos = userReposViewState.dataState!!,
                    searchQuery = "octocat",
                    onSearchQueryChange = {},
                    getUserInfo = { },
                    navToUserDetails = {})
            }

        }
        // Assert user image container and repository list are present
        rule.onNodeWithTag(Testags.UserImageContainer, useUnmergedTree = true).assertExists()
        rule.onNodeWithTag(Testags.RepoList,useUnmergedTree = true).assertExists()
    }

}