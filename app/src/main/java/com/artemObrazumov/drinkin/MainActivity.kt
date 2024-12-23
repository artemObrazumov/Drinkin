package com.artemObrazumov.drinkin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.artemObrazumov.drinkin.core.presentation.components.menu.DashboardMenu
import com.artemObrazumov.drinkin.core.presentation.components.menu.MenuWithCart
import com.artemObrazumov.drinkin.core.presentation.components.menu.MenuWithProfile
import com.artemObrazumov.drinkin.core.presentation.menu.MenuViewModel
import com.artemObrazumov.drinkin.dashboard.presentation.cart.CartScreen
import com.artemObrazumov.drinkin.dashboard.presentation.cart.CartScreenViewModel
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.ProductDetailsScreen
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.ProductDetailsViewModel
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.ProductListScreen
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.ProductListViewModel
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkinTheme {
                enableEdgeToEdge()
                val navController = rememberNavController()
                val menuViewModel: MenuViewModel = koinViewModel()
                val menuState by menuViewModel.state.collectAsState()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = DashBoard,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None }
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
                                        basketHasElements = menuState.basketHasElements,
                                        onAddressIconClicked = {},
                                        onProfileIconClicked = {},
                                        onCartIconClicked = { navController.navigate(Cart) }
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
                                        basketHasElements = menuState.basketHasElements,
                                        onBackButtonClicked = onBackPress,
                                        onCartIconClicked = { navController.navigate(Cart) }
                                    )
                                }
                            )
                        }

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
                                        onProfileIconClicked = {  }
                                    )
                                },
                                onIncrementProduct = viewModel::incrementProduct,
                                onDecrementProduct = viewModel::decrementProduct,
                                onRemoveProduct = viewModel::removeProduct,
                                onViewProductDetails = viewModel::showProductDetails,
                                onHideDetails = viewModel::hideProductDetails
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
data object DashBoard

@Serializable
data class Details(
    val productId: Int
)

@Serializable
data object Cart

@Serializable
data object Address