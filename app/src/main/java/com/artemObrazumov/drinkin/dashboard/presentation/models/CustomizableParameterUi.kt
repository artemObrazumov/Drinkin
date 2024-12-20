package com.artemObrazumov.drinkin.dashboard.presentation.models

import com.artemObrazumov.drinkin.core.presentation.FormattedValue
import com.artemObrazumov.drinkin.core.presentation.asFormattedPrice
import com.artemObrazumov.drinkin.core.utils.Constants.PRICE_UNIT
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameter
import com.artemObrazumov.drinkin.dashboard.domain.models.CustomizableParameterOption

data class CustomizableParameterUi(
    val name: String,
    val options: List<CustomizableParameterOptionUi>
)

fun CustomizableParameter.toCustomizableParameterUi(): CustomizableParameterUi {
    return CustomizableParameterUi(
        name = name,
        options = options.map {
            it.toCustomizableParameterOptionUi()
        }
    )
}

data class CustomizableParameterOptionUi(
    val name: String,
    val detail: String,
    val imageRes: Int,
    val priceDifference: FormattedValue<Float>
)

fun CustomizableParameterOption.toCustomizableParameterOptionUi(): CustomizableParameterOptionUi {
    return CustomizableParameterOptionUi(
        name = name,
        detail = detail,
        imageRes = imageRes,
        priceDifference = priceDifference.asFormattedPrice(PRICE_UNIT)
    )
}