package com.artemObrazumov.drinkin.address.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.address.presentation.addressSelect.AddressSelectScreen

fun NavGraphBuilder.addressGraph(
    navController: NavController
) {
    composable<AddressSelect> {
        AddressSelectScreen()
    }
}