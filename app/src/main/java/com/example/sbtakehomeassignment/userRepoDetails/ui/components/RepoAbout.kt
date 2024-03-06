package com.example.sbtakehomeassignment.userRepoDetails.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sbtakehomeassignment.R
import com.example.sbtakehomeassignment.common.utils.Testags
import com.example.sbtakehomeassignment.ui.theme.SBTakeHomeAssignmentTheme
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo

@Composable
fun RepoAbout(userRepo: UserRepo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp).testTag(Testags.UserRepoDetailsAbout),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.about), style = MaterialTheme.typography.titleLarge.copy(
                color = Color.DarkGray, fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = userRepo.description ?: "", style = MaterialTheme.typography.titleSmall.copy(
                color = Color.Gray, fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRepoAbout() {
    SBTakeHomeAssignmentTheme {
        RepoAbout(userRepo = UserRepo.getMockData())
    }
}