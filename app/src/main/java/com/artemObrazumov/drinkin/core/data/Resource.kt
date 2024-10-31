package com.artemObrazumov.drinkin.core.data

import androidx.annotation.ColorInt
import androidx.annotation.StringRes

sealed interface Resource {

    val reference: Int

    @JvmInline
    value class StringResource(
        @StringRes override val reference: Int
    ): Resource
}