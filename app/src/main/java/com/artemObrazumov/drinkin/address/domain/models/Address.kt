package com.artemObrazumov.drinkin.address.domain.models

data class Address(
    val cafeId: Int,
    val longitude: Double,
    val latitude: Double,
    val address: String
)