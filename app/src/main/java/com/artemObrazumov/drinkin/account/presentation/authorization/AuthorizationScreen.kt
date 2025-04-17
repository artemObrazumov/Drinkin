package com.artemObrazumov.drinkin.account.presentation.authorization

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artemObrazumov.drinkin.account.presentation.login.LoginScreen
import com.artemObrazumov.drinkin.account.presentation.login.LoginScreenViewModel
import com.artemObrazumov.drinkin.account.presentation.registration.RegistrationScreen
import com.artemObrazumov.drinkin.core.presentation.components.Switch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.log

@Composable
fun AuthorizationScreen(
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 2 })

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    val viewModel: LoginScreenViewModel = koinViewModel()
                    val state by viewModel.state.collectAsState()
                    LoginScreen(
                        state = state,
                        onLoginChanged = { login ->
                            viewModel.updateLogin(login)
                        },
                        onPasswordChanged = { password ->
                            viewModel.updatePassword(password)
                        },
                        onPasswordToggled = {
                            viewModel.togglePassword()
                        },
                        onLogin = {
                            viewModel.doLogin()
                        }
                    )
                }
                1 -> RegistrationScreen()
            }
        }

        Switch(
            options = Pair("Login", "Registration"),
            currentOption = currentPage,
            onOptionClicked = {
                currentPage = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 48.dp)
        )
    }
}