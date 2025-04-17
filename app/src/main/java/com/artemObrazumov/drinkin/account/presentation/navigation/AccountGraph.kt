package com.artemObrazumov.drinkin.account.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artemObrazumov.drinkin.account.presentation.authorization.AuthorizationScreen
import com.artemObrazumov.drinkin.account.presentation.login.LoginScreen

fun NavGraphBuilder.accountGraph(
    navController: NavController
) {

    composable<Authorization> {
        AuthorizationScreen()
    }
}