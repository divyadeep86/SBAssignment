package com.example.sbtakehomeassignment.common.navigation

import androidx.navigation.NavHostController

// Define route names in an object for better organization and to avoid hardcoding strings
object AppDestinations {
    const val USERINFO_ROUTE = "userInfo"
    const val USER_REPO_DETAILS_ROUTE = "userRepoDetails"
}

// Function to set up the NavHostController with navigation actions
class AppNavigationActions(navController: NavHostController) {
    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
    val navigateFromUserInfoToDetails: () -> Unit = {
        navController.navigate(AppDestinations.USER_REPO_DETAILS_ROUTE)
    }
}