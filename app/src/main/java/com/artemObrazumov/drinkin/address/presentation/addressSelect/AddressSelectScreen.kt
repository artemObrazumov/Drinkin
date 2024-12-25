package com.artemObrazumov.drinkin.address.presentation.addressSelect

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.artemObrazumov.drinkin.address.presentation.addressSelect.components.CafeDetailsScreen
import com.artemObrazumov.drinkin.address.presentation.addressSelect.components.cafeMarkerView
import com.artemObrazumov.drinkin.address.presentation.models.CafeUi
import com.artemObrazumov.drinkin.core.presentation.LoadingScreen
import com.artemObrazumov.drinkin.core.presentation.components.BeansBackground
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider

@Composable
fun AddressSelectScreen(
    state: AddressSelectScreenState,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit = {},
    onCafeClicked: (cafe: CafeUi) -> Unit = {},
    onCafeSelected: (cafe: CafeUi) -> Unit = {}
) {
    BeansBackground()
    when (state) {
        AddressSelectScreenState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        is AddressSelectScreenState.Content -> {
            AddressSelectScreenContent(
                cafes = state.cafes,
                selectedCafe = state.selectedCafe ?: state.cafes.first(),
                modifier = modifier,
                onCafeClicked = onCafeClicked,
                onCafeSelected = onCafeSelected
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
    selectedCafe: CafeUi,
    modifier: Modifier = Modifier,
    onCafeClicked: (cafe: CafeUi) -> Unit,
    onCafeSelected: (cafe: CafeUi) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(null) {
        MapKitFactory.initialize(context)
        MapKitFactory.getInstance().onStart()
    }

    var selectedCafePosition by remember {
        mutableStateOf(Point(0.0, 0.0))
    }

    var mapView by remember {
        mutableStateOf<MapView?>(null)
    }

    val bottomSheetState = rememberBottomSheetScaffoldState()
    LaunchedEffect(selectedCafe) {
        selectedCafePosition = Point(selectedCafe.latitude, selectedCafe.longitude)
        bottomSheetState.bottomSheetState.expand()
        mapView?.let { view ->
            view.mapWindow.map.move(
                CameraPosition(selectedCafePosition, 17f, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 0.3f),
            ) {}
        }
    }
    BottomSheetScaffold(
        sheetContent = {
            CafeDetailsScreen(
                cafe = selectedCafe,
                onCafeSelected = { onCafeSelected(selectedCafe) }
            )
        },
        scaffoldState = bottomSheetState,
        sheetPeekHeight = 86.dp
    ) {
        val markerColor = MaterialTheme.colorScheme.primary
        var addedCafePointsToMap by remember {
            mutableStateOf(false)
        }
        val markerList = remember {
            mutableStateListOf<PlacemarkMapObject>()
        }
        val listenerList = remember {
            mutableStateListOf<MapObjectTapListener>()
        }
        LaunchedEffect(mapView) {
            mapView?.let { view ->
                if (!addedCafePointsToMap) {
                    addedCafePointsToMap = true
                    val markerView = cafeMarkerView(context, markerColor.toArgb())
                    cafes.forEach { cafe ->
                        val placemark = view.mapWindow.map.mapObjects.addPlacemark(
                            Point(cafe.latitude, cafe.longitude),
                            ViewProvider(markerView)
                        )
                        val listener = MapObjectTapListener { _, _ ->
                            onCafeClicked(cafe)
                            false
                        }
                        placemark.addTapListener(listener)
                        markerList.add(placemark)
                        listenerList.add(listener)
                    }
                }
            }
        }
        AndroidView(
            modifier = modifier.fillMaxSize(),
            factory = {
                mapView = MapView(it)
                mapView!!
            }
        )
    }
}