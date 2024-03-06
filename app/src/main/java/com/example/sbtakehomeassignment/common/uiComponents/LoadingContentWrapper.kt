package com.example.sbtakehomeassignment.common.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * - Purpose: `LoadingContentWrapper` is a Jetpack Compose utility for managing UI states.
 * - isLoading Parameter: Controls whether to show a loading indicator or the content.
 * - dialogTitle Parameter: Sets the title for an optional dialog.
 * - message Parameter: The text message to display inside the dialog.
 * - showDialog Parameter: Determines if a dialog should be displayed.
 * - dismissDialog Function: Callback for when the dialog is dismissed.
 * - content Lambda: The main UI content to display when not in a loading state.
 * - Box Composable: Used as a container to overlay the loading indicator or content.
 * - CircularProgressIndicator: Shown when `isLoading` is true, indicating a loading process.
 * - AlertDialog: Displayed when `showDialog` is true, providing user feedback or information.
 */

@Composable
fun LoadingContentWrapper(
    isLoading: Boolean,
    dialogTitle: String = "",
    errorMessage: String?,
    showDialog: Boolean = false,
    dismissDialog: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()
        // Show content or loading indicator based on isLoading state
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp),
                strokeWidth = 4.dp
            )
        }



        if (showDialog && errorMessage != null) {
            AlertDialog(
                onDismissRequest = dismissDialog,
                title = { Text(dialogTitle) },
                text = { Text(errorMessage) },
                confirmButton = {
                    Button(onClick = dismissDialog) {
                        Text("OK")
                    }
                }
            )
        }
    }

}