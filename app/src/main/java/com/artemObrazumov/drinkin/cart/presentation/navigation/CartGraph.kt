package com.artemObrazumov.drinkin.cart.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.core.presentation.components.menu.MenuWithProfile
import com.artemObrazumov.drinkin.cart.presentation.cart.CartScreen
import com.artemObrazumov.drinkin.cart.presentation.cart.CartScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.cartGraph(
    navController: NavController,
    addressDestination: () -> @Serializable Any,
    makeOrderDestination : () -> @Serializable Any,
    accountDestination : () -> @Serializable Any
) {
    composable<Cart>(
        enterTransition = { scaleIn(initialScale = 0.95f) + fadeIn() },
        exitTransition = { scaleOut(targetScale = 0.95f) + fadeOut() }
    ) {
        val viewModel: CartScreenViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()
        CartScreen(
            state = state,
            menu = {
                MenuWithProfile(
                    title = "Cart",
                    onBackButtonClicked = { navController.navigateUp() },
                    onProfileIconClicked = { navController.navigate(accountDestination()) }
                )
            },
            onAddressClicked = { navController.navigate(addressDestination()) },
            onIncrementProduct = viewModel::incrementProduct,
            onDecrementProduct = viewModel::decrementProduct,
            onRemoveProduct = viewModel::removeProduct,
            onViewProductDetails = viewModel::showProductDetails,
            onHideDetails = viewModel::hideProductDetails,
            onMakeOrder = { navController.navigate(makeOrderDestination()) }
        )
    }
}