package com.artemObrazumov.drinkin.account.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.account.presentation.account.AccountScreen
import com.artemObrazumov.drinkin.account.presentation.account.AccountScreenViewModel
import com.artemObrazumov.drinkin.account.presentation.authorization.AuthorizationScreen
import com.artemObrazumov.drinkin.core.presentation.components.menu.EmptyMenu
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.accountGraph(
    navController: NavController,
    startupScreenDestination: () -> @Serializable Any,
    ordersScreenDestination: () -> @Serializable Any
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

    composable<Account>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        val viewModel: AccountScreenViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        AccountScreen(
            state = state,
            menu = {
                EmptyMenu(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background),
                    title = "Account",
                    onBackButtonClicked = { navController.navigateUp() }
                )
            },
            onGoToOrders = { navController.navigate(ordersScreenDestination()) }
        )
    }
}