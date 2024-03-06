package com.example.sbtakehomeassignment.userInfo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sbtakehomeassignment.R
import com.example.sbtakehomeassignment.common.uiComponents.CounterChip
import com.example.sbtakehomeassignment.ui.theme.SBTakeHomeAssignmentTheme
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userRepoDetails.ui.components.RepoHeader

@Composable
fun RepoItem(userRepo: UserRepo, onclickItem: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 8.dp, shape = RoundedCornerShape(4.dp)
            )
            .background(Color.White)
            .padding(14.dp)
            .defaultMinSize(minHeight = 60.dp)
            .clickable(enabled = true,
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onclickItem(userRepo.id) }), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = userRepo.name, style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.DarkGray, fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = userRepo.description ?: "", style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.DarkGray, fontWeight = FontWeight.Normal
                )
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRepoItem() {
    SBTakeHomeAssignmentTheme {
        RepoItem(userRepo = UserRepo.getMockData(),{})
    }
}