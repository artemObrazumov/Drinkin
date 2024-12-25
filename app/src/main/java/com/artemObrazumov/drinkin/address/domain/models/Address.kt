package com.artemObrazumov.drinkin.address.domain.models

import com.artemObrazumov.drinkin.address.presentation.models.CafeUi

data class Address(
    val cafeId: Int,
    val latitude: Double,
    val longitude: Double,
    val address: String
)

fun CafeUi.toAddress(): Address {
    return Address(
        cafeId = id,
        latitude = latitude,
        longitude = longitude,
        address = address
    )
}