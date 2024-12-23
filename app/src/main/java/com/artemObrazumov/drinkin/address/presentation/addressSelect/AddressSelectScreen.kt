package com.artemObrazumov.drinkin.address.presentation.addressSelect

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

@Composable
fun AddressSelectScreen(
    modifier: Modifier = Modifier
) {
    MapKitFactory.initialize(LocalContext.current)
    MapKitFactory.getInstance().onStart()
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            MapView(it)
        }
    )
}