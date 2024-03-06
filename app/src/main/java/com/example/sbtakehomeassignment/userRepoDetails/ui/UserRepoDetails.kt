package com.example.sbtakehomeassignment.userRepoDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sbtakehomeassignment.R.drawable.baseline_arrow_back_24
import com.example.sbtakehomeassignment.common.utils.Testags
import com.example.sbtakehomeassignment.ui.theme.SBTakeHomeAssignmentTheme
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userRepoDetails.ui.components.RepoAbout
import com.example.sbtakehomeassignment.userRepoDetails.ui.components.RepoHeader
/**
 * Purpose: Displays detailed information about a specific repository. It includes a back button for navigation and sections for the repository's header and additional details.
 *
 * How It Works:
 * Column Layout: The main layout is a Column that arranges its children vertically. It uses Modifier.fillMaxSize() to occupy the entire screen space.
 *
 * Back Button:
 * An IconButton that triggers navigateBack when clicked, allowing the user to return to the previous screen.
 * Contains an Icon with a back arrow image, indicating its purpose.
 *
 * Repository Details:
 * A nested Column holds the detailed information about the repository. It fills the width and height of its parent and applies padding for spacing.
 * The RepoHeader composable displays the repository's primary information, such as name and description.
 * A Divider visually separates the header from the rest of the details.
 * The RepoAbout composable provides additional information about the repository, like the number of stars and forks.
 *
 * Key Components:
 * IconButton & Icon: Used together to create a clickable back button with a visual icon.
 * RepoHeader: Custom composable that displays the repository's main information.
 * Divider: A horizontal line used to separate different sections of the UI for better visual organization.
 * RepoAbout: Custom composable that presents more detailed information about the repository.
 * */
@Composable
fun UserRepoDetailsScreen(userRepo: UserRepo?, navigateBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().testTag(Testags.UserRepoDetailsScreen),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(
            onClick = navigateBack, modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                painter = painterResource(id = baseline_arrow_back_24),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            userRepo?.run {
                RepoHeader(userRepo = userRepo)
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(color = Color.LightGray)
                )
                RepoAbout(userRepo = userRepo)
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewUserRepoDetailsScreen() {
    SBTakeHomeAssignmentTheme {
        UserRepoDetailsScreen(userRepo = UserRepo.getMockData(), {})
    }
}
