package com.artemObrazumov.drinkin.address.domain.models

import java.time.LocalTime

data class Cafe(
    val id: Int,
    val address: String,
    val workStartTime: LocalTime,
    val workEndTime: LocalTime,
    val contactNumber: String,
    val latitude: Double,
    val longitude: Double
)
