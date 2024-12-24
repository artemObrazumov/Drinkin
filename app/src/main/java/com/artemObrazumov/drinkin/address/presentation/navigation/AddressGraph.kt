package com.artemObrazumov.drinkin.address.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.address.presentation.addressSelect.AddressSelectScreen
import com.artemObrazumov.drinkin.address.presentation.addressSelect.AddressSelectViewModel
import com.artemObrazumov.drinkin.cart.presentation.cart.CartScreenViewModel
import com.artemObrazumov.drinkin.core.presentation.components.menu.MenuWithCart
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.addressGraph(
    navController: NavController,
    cartDestination: () -> @Serializable Any,
) {
    composable<AddressSelect>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        val viewModel: AddressSelectViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        AddressSelectScreen(
            state = state,
            menu = {
                MenuWithCart(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background),
                    title = "Select address",
                    onBackButtonClicked = { navController.navigateUp() },
                    onCartIconClicked = { navController.navigate(cartDestination()) }
                )
            }
        )
    }
}