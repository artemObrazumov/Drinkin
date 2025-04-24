package com.artemObrazumov.drinkin.startup.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground

@Composable
fun StartupScreen(
    modifier: Modifier = Modifier
) {
    BeansBackground()
    LoadingScreen(modifier = modifier)
}
