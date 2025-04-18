package com.artemObrazumov.drinkin.startup.presentation.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.startup.presentation.StartupScreen
import com.artemObrazumov.drinkin.startup.presentation.StartupScreenIntent
import com.artemObrazumov.drinkin.startup.presentation.StartupScreenViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.startupGraph(
    navController: NavController,
    onboardingScreenDestination: () -> @Serializable Any,
    authorizationScreenDestination: () -> @Serializable Any,
    dashboardScreenDestination: () -> @Serializable Any
) {

    composable<StartupScreen> {
        val scope = rememberCoroutineScope()
        val viewModel: StartupScreenViewModel = koinViewModel()
        scope.launch {
            viewModel.intents.collect { intent ->
                when(intent) {
                    StartupScreenIntent.Authorization -> {
                        navController.navigate(authorizationScreenDestination()) {
                            popUpTo(0)
                        }
                    }
                    StartupScreenIntent.Onboarding -> {
                        navController.navigate(onboardingScreenDestination()) {
                            popUpTo(0)
                        }
                    }
                    StartupScreenIntent.Dashboard -> {
                        navController.navigate(dashboardScreenDestination()) {
                            popUpTo(0)
                        }
                    }
                }
            }
        }
        StartupScreen()
    }
}