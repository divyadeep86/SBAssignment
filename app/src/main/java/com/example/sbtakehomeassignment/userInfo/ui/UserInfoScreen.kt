package com.example.sbtakehomeassignment.userInfo.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sbtakehomeassignment.common.uiComponents.AnimateContent
import com.example.sbtakehomeassignment.common.uiComponents.AsyncImageLoader
import com.example.sbtakehomeassignment.common.uiComponents.TextInput
import com.example.sbtakehomeassignment.ui.theme.SBTakeHomeAssignmentTheme
import com.example.sbtakehomeassignment.userInfo.domain.models.UserInfo
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userInfo.ui.components.RepoItem
import com.example.sbtakehomeassignment.userRepoDetails.ui.UserRepoDetailsScreen
import java.time.format.TextStyle


/**
 * Purpose: Displays the user information and repository list UI, allowing for user search input, displaying user details, and listing repositories with navigation to detailed views.
 * How It Works:
 * Keyboard Controller: Manages the software keyboard visibility.
 * Column Layout: Arranges UI elements vertically with spacing between them. Includes a clickable area to hide the keyboard when tapped outside input fields.
 *
 * Search Input and Button:
 * A row containing a text input field for entering a GitHub user ID and a search button.
 * The search button triggers the getUserInfo function and hides the keyboard.
 *
 * User Information Display:
 * Conditionally displayed based on the presence of an avatar URL.
 * Shows the user's avatar and name using AsyncImageLoader for image loading and a Text composable for the name.
 *
 * Repositories List:
 * Conditionally displayed based on the non-empty state of the userRepos list.
 * Uses a LazyColumn to list repositories, each item triggering navToUserDetails upon click.
 *
 * Key Components:
 * TextInput: Custom composable for text input, bound to searchQuery and updates on change via onSearchQueryChange.
 * AnimateContent: Custom composable that conditionally animates content visibility based on a boolean flag.
 * AsyncImageLoader: Custom composable for asynchronous image loading and display.
 * RepoItem: Custom composable representing a single repository item in the list, handling click actions to navigate to details.
 * */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInfoScreen(
    userInfo: UserInfo,
    userRepos: List<UserRepo>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    getUserInfo: () -> Unit,
    navToUserDetails: (Int) -> Unit
) {
    // Keyboard controller for hiding the keyboard
    val keyboardController = LocalSoftwareKeyboardController.current
    // Main column layout
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable(
                enabled = true,
                onClick = { keyboardController?.hide() },
                interactionSource = MutableInteractionSource(),
                indication = null
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
// Search input and button row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Text input for search query
            TextInput(
                lable = "Enter a github user id",
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.weight(1f)
            )
            // Search button
            Button(
                onClick = {
                    keyboardController?.hide()
                    getUserInfo()
                }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text(
                    "SEARCH",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.DarkGray)
                )
            }
        }
        // User info section with animated visibility
        AnimateContent(visibility = userInfo.avatar_url.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Async image loader for user avatar
                AsyncImageLoader(
                    imageUrl = userInfo.avatar_url,
                    modifier = Modifier.aspectRatio(2f / 1f),
                    contentScale = ContentScale.Inside
                )
                // User name display
                Text(
                    text = userInfo.name, style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.DarkGray, fontWeight = FontWeight.Bold
                    )
                )

            }
        }

        // User repositories list with animated visibility
        AnimateContent(visibility = userRepos.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(18.dp)
            ) {
                items(userRepos) { userRepo ->
                    // Repository item
                    RepoItem(userRepo = userRepo, onclickItem = navToUserDetails)
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewUserInfoScreenScreen() {
    SBTakeHomeAssignmentTheme {
        UserInfoScreen(userInfo = UserInfo.getMockData(), listOf(), "", {}, {}, {})
    }
}
