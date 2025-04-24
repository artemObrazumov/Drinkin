package com.artemObrazumov.drinkin.onboarding.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.onboarding.presentation.OnboardingScreen
import com.artemObrazumov.drinkin.onboarding.presentation.OnboardingScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.onboardingGraph(
    navController: NavController,
    authorizationScreenDestination: () -> @Serializable Any
) {

    composable<Onboarding> {
        val viewModel: OnboardingScreenViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        OnboardingScreen(
            state = state,
            onOnboardingFinished = { navController.navigate(authorizationScreenDestination()) },
            onSkipped = { viewModel.finishOnboarding() }
        )
    }
}