package com.artemObrazumov.drinkin.product.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.artemObrazumov.drinkin.core.presentation.components.menu.DashboardMenu
import com.artemObrazumov.drinkin.core.presentation.components.menu.MenuWithCart
import com.artemObrazumov.drinkin.core.presentation.menu.MenuState
import com.artemObrazumov.drinkin.product.presentation.product_details.ProductDetailsScreen
import com.artemObrazumov.drinkin.product.presentation.product_details.ProductDetailsViewModel
import com.artemObrazumov.drinkin.product.presentation.products_list.ProductListScreen
import com.artemObrazumov.drinkin.product.presentation.products_list.ProductListViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder

fun NavGraphBuilder.dashboardGraph(
    navController: NavController,
    menuState: () -> MenuState,
    cartDestination: () -> @Serializable Any,
    addressDestination: () -> @Serializable Any,
    accountDestination: () -> @Serializable Any
) {
    composable<DashBoard> {
        val viewModel: ProductListViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        ProductListScreen(
            state = state,
            modifier = Modifier,
            onDetailsScreen = {
                navController.navigate(Details(1)) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onCategoryClicked = viewModel::changeCategory,
            menu = {
                DashboardMenu(
                    address = menuState().address,
                    basketHasElements = menuState().basketHasElements,
                    onAddressIconClicked = { navController.navigate(addressDestination()) },
                    onProfileIconClicked = { navController.navigate(accountDestination()) },
                    onCartIconClicked = { navController.navigate(cartDestination()) }
                )
            }
        )
    }

    composable<Details> { backStackEntry ->
        val details = backStackEntry.toRoute<Details>()
        val viewModel: ProductDetailsViewModel = koinViewModel(
            parameters = {
                ParametersHolder(
                    mutableListOf(details.productId)
                )
            }
        )
        val state by viewModel.state.collectAsState()
        ProductDetailsScreen(
            state = state,
            onGoBack = {
                navController.navigateUp()
            },
            incrementCount = viewModel::incrementCount,
            decrementCount = viewModel::decrementCount,
            onParameterSelect = viewModel::onParameterSelect,
            addToCart = viewModel::addToCart,
            menu = { onBackPress ->
                MenuWithCart(
                    title = "Details",
                    basketHasElements = menuState().basketHasElements,
                    onBackButtonClicked = onBackPress,
                    onCartIconClicked = { navController.navigate(cartDestination()) }
                )
            }
        )
    }
}