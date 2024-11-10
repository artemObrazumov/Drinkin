package com.artemObrazumov.drinkin

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
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.PRODUCT_DETAILS
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.ProductDetailsScreen
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.PRODUCTS
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.ProductListScreen
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.ProductListViewModel
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
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
                                    navController.navigate(Details) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }

                        composable<Details> {
                            ProductDetailsScreen(
                                productDetailsUi = PRODUCT_DETAILS,
                                onGoBack = {
                                    navController.navigateUp()
                                },
                                count = 0,
                                selectedParameters = emptyMap()
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
data object Details