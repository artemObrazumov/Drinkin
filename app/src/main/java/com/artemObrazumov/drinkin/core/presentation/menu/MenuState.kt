package com.artemObrazumov.drinkin.core.presentation.menu

import androidx.compose.runtime.Immutable

@Immutable
data class MenuState(
    val basketHasElements: Boolean = false
)