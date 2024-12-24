package com.artemObrazumov.drinkin.address.presentation.models

import com.artemObrazumov.drinkin.address.domain.models.Cafe
import java.time.format.DateTimeFormatter

data class CafeUi(
    val id: Int,
    val address: String,
    val workTime: String,
    val contactNumber: String,
    val latitude: Double,
    val longitude: Double
)

fun Cafe.toCafeUi(): CafeUi {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val startTimeFormatted = formatter.format(workStartTime)
    val endTimeFormatted = formatter.format(workEndTime)

    return CafeUi(
        id = id,
        address = address,
        workTime = "$startTimeFormatted-$endTimeFormatted",
        contactNumber = contactNumber,
        latitude = latitude,
        longitude = longitude
    )
}
