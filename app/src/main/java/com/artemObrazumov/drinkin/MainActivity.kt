package com.artemObrazumov.drinkin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.PRODUCT_DETAILS
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.ProductDetailsScreen
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.ProductDetailsViewModel
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.PRODUCTS
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
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
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
                                }
                            )
                        }

                        composable<Details> { backStackEntry ->
                            val details = backStackEntry.toRoute<Details>()
                            val viewModel: ProductDetailsViewModel = koinViewModel(
                                parameters = { ParametersHolder(
                                    mutableListOf(details.productId)
                                ) }
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
                                addToCart = viewModel::addToCart
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