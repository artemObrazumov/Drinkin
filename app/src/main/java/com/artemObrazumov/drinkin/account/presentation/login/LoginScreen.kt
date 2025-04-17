package com.artemObrazumov.drinkin.account.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.presentation.components.TextField
import com.artemObrazumov.drinkin.core.presentation.resolve

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    onLoginChanged: (login: String) -> Unit = {},
    onPasswordChanged: (password: String) -> Unit = {},
    onPasswordToggled: () -> Unit = {},
    onLogin: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val (passwordField) = remember { FocusRequester.createRefs() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 48.dp)
            .padding(bottom = 16.dp)
    ) {
        Spacer(
            modifier = modifier
                .height(48.dp)
        )
        Text(
            text = stringResource(R.string.login_title),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(
            modifier = modifier
                .height(64.dp)
        )
        Text(
            text = stringResource(R.string.login_subtitle),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(
            modifier = modifier
                .height(64.dp)
        )
        TextField(
            value = state.login,
            onValueChanged = onLoginChanged,
            placeHolder = stringResource(R.string.login),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { passwordField.requestFocus() }
            )
        )
        Spacer(
            modifier = modifier
                .height(18.dp)
        )
        TextField(
            modifier = Modifier
                .focusRequester(passwordField),
            value = state.password,
            onValueChanged = onPasswordChanged,
            placeHolder = stringResource(R.string.password),
            afterButton = {
                Icon(
                    painterResource(R.drawable.eye),
                    contentDescription = stringResource(R.string.toggle_password),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onPasswordToggled
                        )
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )
        Spacer(
            modifier = modifier
                .height(32.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                if (!state.isLoading) {
                    onLogin()
                }
            }
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp),
                    color = Color.White
                )
            } else {
                Text(
                    text = stringResource(R.string.login),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
        Spacer(
            modifier = modifier
                .height(18.dp)
        )
        state.error?.let {
            Text(
                text = it.resolve(LocalContext.current),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}