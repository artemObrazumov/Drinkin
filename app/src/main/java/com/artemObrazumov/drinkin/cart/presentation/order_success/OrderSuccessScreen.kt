package com.artemObrazumov.drinkin.cart.presentation.order_success

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.app.ui.theme.DrinkinTheme
import kotlinx.coroutines.delay

@Composable
fun OrderSuccessScreen(
    modifier: Modifier = Modifier,
    onOrdersClicked: () -> Unit = {},
    menu: @Composable () -> Unit = {},
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.checkout)
    )

    var displayHeaderText by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(1000)
        displayHeaderText = true
    }

    var displaySubtitleTextAndButtons by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(2000)
        displaySubtitleTextAndButtons = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(96.dp)
        )
        AnimatedVisibility(displayHeaderText) {
            Column {
                Spacer(
                    modifier = Modifier
                        .height(28.dp)
                )
                Text(
                    text = stringResource(R.string.order_confirmed),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Color.White
                )
            }
        }

        AnimatedVisibility(displaySubtitleTextAndButtons) {
            Column {
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Text(
                    text = stringResource(R.string.order_confirmed_subtitle),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier
                        .height(28.dp)
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.White
                    ),
                    onClick = onOrdersClicked
                ) {
                    Text(
                        text = stringResource(R.string.go_to_orders),
                        color = Color.Black
                    )
                }
            }
        }
    }

    menu()
}

@PreviewLightDark
@Composable
fun OrderSuccessScreenPreview(
    modifier: Modifier = Modifier
) {
    DrinkinTheme {
        OrderSuccessScreen()
    }
}