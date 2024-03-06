package com.example.sbtakehomeassignment.common.uiComponents



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset

@Composable
fun AnimateContent(
    visibility: Boolean, content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn() + slideIn(initialOffset = { size -> IntOffset(x = 0, y = 50) }),
        exit = fadeOut()
    ) {
        content()
    }
}