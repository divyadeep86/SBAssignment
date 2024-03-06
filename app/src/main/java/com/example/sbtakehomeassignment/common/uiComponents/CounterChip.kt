package com.example.sbtakehomeassignment.common.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sbtakehomeassignment.R
import com.example.sbtakehomeassignment.ui.theme.SBTakeHomeAssignmentTheme
import com.example.sbtakehomeassignment.userInfo.domain.models.UserRepo
import com.example.sbtakehomeassignment.userRepoDetails.ui.components.RepoHeader

@Composable
fun CounterChip(
    leadingicon: Int, title: String, counterValue: String, textColor: Color = Color.DarkGray
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = leadingicon),
            modifier = Modifier.size(24.dp),
            contentDescription = "",
            tint = Color.Gray
        )
        Text(
            text = title, style = MaterialTheme.typography.titleSmall.copy(color = textColor)
        )

        Text(
            modifier = Modifier.wrapContentSize(),
            text = counterValue,
            style = MaterialTheme.typography.titleSmall.copy(color = textColor)
        )
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewCounterChip() {
    SBTakeHomeAssignmentTheme {
        CounterChip(
            leadingicon = R.drawable.fork,
            title = stringResource(R.string.fork),
            counterValue = "100"
        )
    }
}