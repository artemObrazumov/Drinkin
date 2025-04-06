package com.artemObrazumov.drinkin.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.artemObrazumov.drinkin.address.presentation.navigation.AddressSelect
import com.artemObrazumov.drinkin.address.presentation.navigation.addressGraph
import com.artemObrazumov.drinkin.cart.presentation.navigation.Cart
import com.artemObrazumov.drinkin.cart.presentation.navigation.cartGraph
import com.artemObrazumov.drinkin.core.presentation.menu.MenuViewModel
import com.artemObrazumov.drinkin.product.presentation.navigation.DashBoard
import com.artemObrazumov.drinkin.product.presentation.navigation.dashboardGraph
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme
import com.artemObrazumov.drinkin.order.presentation.navigation.NewOrder
import com.artemObrazumov.drinkin.order.presentation.navigation.orderGraph
import org.koin.androidx.compose.koinViewModel

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
                        exitTransition = { ExitTransition.None },
                        builder = {
                            dashboardGraph(
                                navController = navController,
                                menuState = { menuState },
                                cartDestination = { Cart },
                                addressDestination = { AddressSelect }
                            )
                            cartGraph(
                                navController = navController,
                                addressDestination = { AddressSelect },
                                makeOrderDestination = { NewOrder }
                            )
                            orderGraph(
                                navController = navController,
                                orderScreenDestination = {}
                            )
                            addressGraph(
                                navController = navController
                            )
                        }
                    )
                }
            }
        }
    }
}