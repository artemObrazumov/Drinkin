package com.artemObrazumov.drinkin.onboarding.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun OnboardingScreen(
    state: OnboardingScreenState,
    modifier: Modifier = Modifier,
    onOnboardingFinished: () -> Unit = {},
    onSkipped: () -> Unit = {}
) {
    LaunchedEffect(state.finishedOnboarding) {
        if (state.finishedOnboarding) {
            onOnboardingFinished.invoke()
        }
    }
    Column {
        Text("onboarding")
        Button(
            onClick = onSkipped
        ) {
            Text(text = "skip")
        }
    }
}