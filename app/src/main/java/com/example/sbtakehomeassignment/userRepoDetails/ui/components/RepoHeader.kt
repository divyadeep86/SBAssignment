package com.example.sbtakehomeassignment.userRepoDetails.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sbtakehomeassignment.R
import com.example.sbtakehomeassignment.common.uiComponents.AsyncImageLoader
import com.example.sbtakehomeassignment.common.uiComponents.CounterChip
import com.example.sbtakehomeassignment.common.utils.Testags
import com.example.sbtakehomeassignment.ui.theme.SBTakeHomeAssignmentTheme
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userRepoDetails.ui.UserRepoDetailsScreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RepoHeader(userRepo: UserRepo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .testTag(Testags.UserRepoDetailsHeader)
            .padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 8.dp)
        ) {
            AsyncImageLoader(
                imageUrl = userRepo.avatarUrl,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit,
                circleCropTransformation = true
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = userRepo.name, style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.DarkGray, fontWeight = FontWeight.Bold
                    ), maxLines = 2
                )
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    text = userRepo.visibility,
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.DarkGray)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CounterChip(
                        leadingicon = R.drawable.visibility,
                        title = stringResource(R.string.watch),
                        counterValue = userRepo.watchCount
                    )
                    CounterChip(
                        leadingicon = R.drawable.fork,
                        title = stringResource(R.string.fork),
                        counterValue = userRepo.forksCount
                    )
                    CounterChip(
                        leadingicon = R.drawable.star,
                        title = stringResource(R.string.star),
                        counterValue = userRepo.starCount
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRepoHeader() {
    SBTakeHomeAssignmentTheme {
        RepoHeader(userRepo = UserRepo.getMockData())
    }
}