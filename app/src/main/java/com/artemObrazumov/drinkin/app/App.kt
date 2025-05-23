package com.artemObrazumov.drinkin.app

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.artemObrazumov.drinkin.account.presentation.navigation.Account
import com.artemObrazumov.drinkin.account.presentation.navigation.Authorization
import com.artemObrazumov.drinkin.account.presentation.navigation.accountGraph
import com.artemObrazumov.drinkin.address.presentation.navigation.AddressSelect
import com.artemObrazumov.drinkin.address.presentation.navigation.addressGraph
import com.artemObrazumov.drinkin.cart.presentation.navigation.Cart
import com.artemObrazumov.drinkin.cart.presentation.navigation.cartGraph
import com.artemObrazumov.drinkin.core.presentation.account.AccountState
import com.artemObrazumov.drinkin.core.presentation.account.AccountViewModel
import com.artemObrazumov.drinkin.core.presentation.menu.MenuViewModel
import com.artemObrazumov.drinkin.onboarding.presentation.navigation.Onboarding
import com.artemObrazumov.drinkin.onboarding.presentation.navigation.onboardingGraph
import com.artemObrazumov.drinkin.order.presentation.navigation.NewOrder
import com.artemObrazumov.drinkin.order.presentation.navigation.Orders
import com.artemObrazumov.drinkin.order.presentation.navigation.orderGraph
import com.artemObrazumov.drinkin.product.presentation.navigation.DashBoard
import com.artemObrazumov.drinkin.product.presentation.navigation.dashboardGraph
import com.artemObrazumov.drinkin.startup.presentation.navigation.StartupScreen
import com.artemObrazumov.drinkin.startup.presentation.navigation.startupGraph
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val menuViewModel: MenuViewModel = koinViewModel()
    val menuState by menuViewModel.state.collectAsState()

    val accountViewModel: AccountViewModel = koinViewModel()
    val accountState by accountViewModel.state.collectAsState()

    LaunchedEffect(accountState) {
        if (accountState is AccountState.Content &&
            (accountState as AccountState.Content).user == null) {
            navController.navigate(StartupScreen) {
                popUpTo(0)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = StartupScreen,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            builder = {
                startupGraph(
                    navController = navController,
                    onboardingScreenDestination = { Onboarding },
                    authorizationScreenDestination = { Authorization },
                    dashboardScreenDestination = { DashBoard }
                )
                onboardingGraph(
                    navController = navController,
                    authorizationScreenDestination = { Authorization }
                )
                accountGraph(
                    navController = navController,
                    startupScreenDestination = { StartupScreen },
                    ordersScreenDestination = { Orders }
                )
                dashboardGraph(
                    navController = navController,
                    menuState = { menuState },
                    cartDestination = { Cart },
                    addressDestination = { AddressSelect },
                    accountDestination = { Account }
                )
                cartGraph(
                    navController = navController,
                    addressDestination = { AddressSelect },
                    makeOrderDestination = { NewOrder },
                    accountDestination = { Account }
                )
                orderGraph(
                    navController = navController,
                    orderScreenDestination = { Orders }
                )
                addressGraph(
                    navController = navController
                )
            }
        )
    }
}