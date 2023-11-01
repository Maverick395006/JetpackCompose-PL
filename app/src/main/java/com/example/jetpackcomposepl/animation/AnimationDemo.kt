package com.example.jetpackcomposepl.animation

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimationComponent() {
    var sizerState by remember {
        mutableStateOf(200.dp)
    }
    val size by animateDpAsState(
        targetValue = sizerState,
        keyframes {
            durationMillis = 2000
            sizerState at 0 with LinearEasing
            sizerState * 1.5f at 1000 with FastOutLinearInEasing
            sizerState * 1f at 1000 with FastOutLinearInEasing
        },
        label = ""
    )
    Box(
        modifier = Modifier
            .size(size)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            sizerState += 50.dp
        }) {
            Text(text = "Increase Size")
        }
    }
}

@Preview(name = "Animation Demo", showSystemUi = true, showBackground = true)
@Composable
fun AnimationPreview() {
    AnimationComponent()
}