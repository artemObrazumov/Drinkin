package com.artemObrazumov.drinkin.address.presentation.addressSelect

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.artemObrazumov.drinkin.address.domain.models.Cafe
import com.artemObrazumov.drinkin.address.presentation.models.CafeUi
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

@Composable
fun AddressSelectScreen(
    state: AddressSelectScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {},
) {
    BeansBackground()
    when (state) {
        AddressSelectScreenState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        is AddressSelectScreenState.Content -> {
            AddressSelectScreenContent(
                cafes = state.cafes,
                modifier = modifier
            )
        }
        is AddressSelectScreenState.Failure -> {}
    }
    menu()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSelectScreenContent(
    cafes: List<CafeUi>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        MapKitFactory.initialize(context)
        MapKitFactory.getInstance().onStart()
    }

    BottomSheetScaffold(
        sheetContent = {

        },
        sheetPeekHeight = 96.dp
    ) {
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = {
                MapView(it)
            }
        )
    }
}