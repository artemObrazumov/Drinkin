package com.artemObrazumov.drinkin.account.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.account.presentation.authorization.AuthorizationScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.accountGraph(
    navController: NavController,
    startupScreenDestination: () -> @Serializable Any
) {

    composable<Authorization> {
        AuthorizationScreen(
            onAuthorization = {
                navController.navigate(startupScreenDestination()) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}