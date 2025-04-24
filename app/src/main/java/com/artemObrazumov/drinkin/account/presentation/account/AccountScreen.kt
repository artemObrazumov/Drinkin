package com.artemObrazumov.drinkin.account.presentation.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.account.domain.models.User
import com.artemObrazumov.drinkin.account.presentation.account.components.LogoutDialog
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.artemObrazumov.drinkin.core.presentation.components.OutlinedBlock

@Composable
fun AccountScreen(
    state: AccountScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {},
    onGoToOrders: () -> Unit = {},
    onLogoutDialogCancelled: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    BeansBackground()
    when (state.userState) {
        is UserState.Content -> {
            AccountScreenContent(
                user = state.userState.user,
                modifier = modifier,
                onGoToOrders = onGoToOrders
            )
        }

        UserState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is UserState.Failure -> {

        }
    }

    if (state.isLogoutDialogOpened) {
        LogoutDialog(
            onDismiss = onLogoutDialogCancelled,
            onConfirm = onLogout
        )
    }

    menu()
}

@Composable
fun AccountScreenContent(
    user: User,
    modifier: Modifier = Modifier,
    onGoToOrders: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 106.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        OutlinedBlock(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.name),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Spacer(
                modifier = Modifier
                    .width(4.dp)
            )
            Text(
                text = user.name,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
        }

        OutlinedBlock(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = stringResource(R.string.go_to_orders),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onGoToOrders
                        )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun AccountScreenPreview() {
    DrinkinTheme {
        AccountScreen(
            state = AccountScreenState(
                userState = UserState.Content(
                    user = USER
                )
            )
        )
    }
}

internal val USER = User(
    name = "test user"
)
