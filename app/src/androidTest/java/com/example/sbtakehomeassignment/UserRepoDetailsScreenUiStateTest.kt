package com.example.sbtakehomeassignment

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sbtakehomeassignment.userInfo.domain.models.UserInfo
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userInfo.ui.UserInfoScreen
import com.example.sbtakehomeassignment.userRepoDetails.ui.UserRepoDetailsScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
/**
 *
 * Purpose of the Class:
 * To ensure that the UserRepoDetailsScreen behaves as expected under different conditions. This includes verifying the presence or absence of UI elements based on the data provided to the screen.
 * Functions and Their Workings:
 *
 * showUserRepoScreen_True_Expected():
 * Purpose: Tests that when the UserRepoDetailsScreen is provided with mock data (simulating a user repository), it correctly displays the main screen, header, and about section.
 * How It Works:
 *
 * rule.setContent { ... }: Sets the content of the test to the UserRepoDetailsScreen, passing in mock data as the user repository.
 *
 * rule.onNodeWithTag(...).assertExists(): Asserts that the nodes (UI elements) with specific tags (UserRepoDetailsScreen, UserRepoDetailsHeader, and UserRepoDetailsAbout) exist in the UI tree. This confirms that the screen and its sections are displayed as expected.
 *
 * headerAndAboutNotShowsIfUserRepoNull_True_Expected():
 * Purpose: Verifies that if the UserRepoDetailsScreen is given a null value for the user repository, it does not display the header and about section, while still displaying the main screen.
 * How It Works:
 * rule.setContent { ... }: Sets the content to the UserRepoDetailsScreen without any data by passing null for the user repository.
 * rule.onNodeWithTag(...).assertExists(): Checks that the main screen tag exists, confirming the screen is still shown.
 * rule.onNodeWithTag(...).assertDoesNotExist(): Asserts that the nodes with tags for the header and about section do not exist, indicating these sections are correctly omitted when there is no data.
 *
 * Summary:
 * This class effectively tests the conditional UI rendering of the UserRepoDetailsScreen, ensuring that it adapts its display based on the availability of data, which is crucial for maintaining a dynamic and responsive user interface.
 * */
@RunWith(AndroidJUnit4::class)
class UserRepoDetailsScreenUiStateTest {
    @get:Rule
    val rule = createComposeRule()


    @Test
    fun showUserRepoScreen_True_Expected() {
        // Set the UI content to the UserRepoDetailsScreen with mock data
        rule.setContent {
            UserRepoDetailsScreen(userRepo = UserRepo.getMockData(),{})
        }
        // Assert that the main screen, header, and about section exist
        rule.onNodeWithTag(Testags.UserRepoDetailsScreen).assertExists()
        rule.onNodeWithTag(Testags.UserRepoDetailsHeader,useUnmergedTree = true).assertExists()
        rule.onNodeWithTag(Testags.UserRepoDetailsAbout,useUnmergedTree = true).assertExists()
    }

    @Test
    fun headerAndAboutNotShowsIfUserRepoNull_True_Expected() {
        // Set the UI content to the UserRepoDetailsScreen without any data (null)
        rule.setContent {
            UserRepoDetailsScreen(userRepo =null,{})
        }
        // Assert that the main screen exists but header and about section do not
        rule.onNodeWithTag(Testags.UserRepoDetailsScreen).assertExists()
        rule.onNodeWithTag(Testags.UserRepoDetailsHeader,useUnmergedTree = true).assertDoesNotExist()
        rule.onNodeWithTag(Testags.UserRepoDetailsAbout,useUnmergedTree = true).assertDoesNotExist()
    }
}