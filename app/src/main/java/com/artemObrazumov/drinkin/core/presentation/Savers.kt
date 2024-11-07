package com.artemObrazumov.drinkin.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

internal val offsetSaver =
    Saver<MutableState<Offset>, List<Float>>(
        save = { offset ->
            listOf(offset.value.x, offset.value.y)
        },
        restore = { value ->
            mutableStateOf(Offset(value[0], value[1]))
        }
    )

@Composable
fun rememberOffsetSaver(): Saver<MutableState<Offset>, List<Float>> =
    remember {
        offsetSaver
    }

internal val intOffsetSaver =
    Saver<MutableState<IntOffset>, List<Int>>(
        save = { offset ->
            listOf(offset.value.x, offset.value.y)
        },
        restore = { value ->
            mutableStateOf(IntOffset(value[0], value[1]))
        }
    )

@Composable
fun rememberIntOffsetSaver(): Saver<MutableState<IntOffset>, List<Int>> =
    remember {
        intOffsetSaver
    }

internal val intSizeSaver =
    Saver<MutableState<IntSize>, List<Int>>(
        save = { offset ->
            listOf(offset.value.width, offset.value.height)
        },
        restore = { value ->
            mutableStateOf(IntSize(value[0], value[1]))
        }
    )

@Composable
fun rememberIntSizeSaver(): Saver<MutableState<IntSize>, List<Int>> =
    remember {
        intSizeSaver
    }