package com.artemObrazumov.drinkin.core.presentation

import java.text.NumberFormat
import java.util.Locale

data class FormattedValue<T>(
    val value: T,
    val formatted: String
)

fun Float.asFormattedPrice(unit: String): FormattedValue<Float> {
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return FormattedValue(
        value = this,
        formatted = "$unit${formatter.format(this)}"
    )
}

fun Int.asFormattedQuantity(): FormattedValue<Int> {
    return FormattedValue(
        value = this,
        formatted = "x $this"
    )
}