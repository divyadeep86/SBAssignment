package com.example.sbtakehomeassignment.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sbtakehomeassignment.common.states.RequestProgressStatus
import com.example.sbtakehomeassignment.common.uiComponents.LoadingContentWrapper
import com.example.sbtakehomeassignment.userInfo.ui.UserInfoScreen
import com.example.sbtakehomeassignment.userInfo.ui.UserInfoViewModel
import com.example.sbtakehomeassignment.userInfo.ui.UserReposViewModel
import com.example.sbtakehomeassignment.userRepoDetails.ui.UserRepoDetailsScreen
/**
 * Purpose: Serves as the main navigation host for the app, defining the navigation graph and managing navigation between different screens based on the app's routing logic.
 * How It Works:
 * Remember Navigation Actions: Initializes AppNavigationActions with the provided NavHostController to handle navigation actions throughout the app.
 *
 *  NavHost Setup:
 * Uses NavHost with the given navController and a start destination to define the navigation structure.
 * Specifies composable destinations using the composable function, mapping routes to screens.
 *
 * UserInfo Route:
 * Retrieves instances of UserInfoViewModel and UserReposViewModel using hiltViewModel().
 * Collects state from both ViewModels to manage UI representation based on the current data and loading/error states.
 * Implements a LaunchedEffect that triggers when the user info request completes, setting the user ID in UserReposViewModel to fetch repositories.
 * Wraps the UserInfoScreen in a LoadingContentWrapper to handle loading and error UI states.
 * Passes relevant data and event handlers to UserInfoScreen, including functions to fetch user info, update the search query, and navigate to repository details.
 *
 * UserRepoDetails Route:
 * Retrieves the UserReposViewModel associated with the parent route (USERINFO_ROUTE) to maintain state consistency across navigation.
 * Collects the selected repository's details using getRepo() from UserReposViewModel.
 * Displays the UserRepoDetailsScreen with the selected repository's details and a navigation back action.
 * */
@Composable
fun AppNavHost(navHostController: NavHostController) {
    // Initialize navigation actions
    val actions = remember { AppNavigationActions(navHostController) }

    // Set up the NavHost with the navigation graph
    NavHost(navController = navHostController, startDestination = AppDestinations.USERINFO_ROUTE) {
        // UserInfo screen composable
        composable(AppDestinations.USERINFO_ROUTE) {
            // ViewModel instances
            val userInfoViewModel: UserInfoViewModel = hiltViewModel()
            val userInfoViewState = userInfoViewModel.viewState.collectAsStateWithLifecycle().value

            val userReposViewModel: UserReposViewModel = hiltViewModel(viewModelStoreOwner = it)
            val userReposViewState = userReposViewModel.viewState.collectAsStateWithLifecycle().value

            // Collect search query state
            val searchQuery = userInfoViewModel.searchQueryFlow().collectAsStateWithLifecycle("").value

            // Trigger fetching user repos on user info completion
            LaunchedEffect(userInfoViewState) {
                if (userInfoViewState.requestProgressStatus == RequestProgressStatus.Completed) {
                    userReposViewModel.setUserId(searchQuery)
                }
            }
            // UI wrapper for loading and error handling
            LoadingContentWrapper(
                isLoading = userInfoViewState.isLoading || userReposViewState.isLoading,
                errorMessage = userInfoViewState.errorMessage,
                showDialog = userInfoViewState.errorMessage != null,
                dismissDialog = userInfoViewModel::clearAllMessages
            ) {
                // UserInfo screen content
                UserInfoScreen(
                    userInfo = userInfoViewState.dataState!!,
                    userRepos = userReposViewState.dataState!!,
                    getUserInfo = userInfoViewModel::getUserInfo,
                    searchQuery = searchQuery,
                    onSearchQueryChange = userInfoViewModel::onSearchQueryChange,
                    navToUserDetails = { repoId ->
                        userReposViewModel.setRepoId(repoId)
                        actions.navigateFromUserInfoToDetails()
                    }
                )
            }

        }
        // UserRepoDetails screen composable
        composable(AppDestinations.USER_REPO_DETAILS_ROUTE) { navBackStack ->
            // Retrieve parent entry for shared ViewModel
            val parentEntry = remember(navBackStack) {
                navHostController.getBackStackEntry(AppDestinations.USERINFO_ROUTE)
            }
            val userReposViewModel: UserReposViewModel = hiltViewModel(parentEntry)
            val userRepo = userReposViewModel.getRepo().collectAsStateWithLifecycle(null).value

            // UserRepoDetails screen content
            UserRepoDetailsScreen(userRepo = userRepo) {
                actions.navigateBack()
            }
        }
    }
}