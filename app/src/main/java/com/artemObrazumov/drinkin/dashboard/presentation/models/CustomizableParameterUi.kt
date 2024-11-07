package com.artemObrazumov.drinkin.dashboard.presentation.models

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
    val imageRes: Int
)

fun CustomizableParameterOption.toCustomizableParameterOptionUi(): CustomizableParameterOptionUi {
    return CustomizableParameterOptionUi(
        name = name,
        detail = detail,
        imageRes = imageRes
    )
}