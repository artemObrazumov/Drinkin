package com.artemObrazumov.drinkin.address.data

import com.artemObrazumov.drinkin.address.domain.data_source.CafeAddressDataSource
import com.artemObrazumov.drinkin.address.domain.models.Address
import com.artemObrazumov.drinkin.address.domain.models.Cafe
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalTime

class CafeAddressMockDataSource: CafeAddressDataSource {

    private val addressScope = CoroutineScope(SupervisorJob())

    private val _activeAddressFlow = MutableSharedFlow<Address?>(
        replay = 1,
        extraBufferCapacity = 1
    ).also { flow ->
        addressScope.launch {
            flow.emit(null)
        }
    }
    private val activeAddressFlow = _activeAddressFlow.asSharedFlow()

    override suspend fun getCafes(): Result<List<Cafe>, NetworkError> {
        delay(1000)
        return Result.Success(CAFES)
    }

    override suspend fun updateActiveAddress(address: Address) {
        _activeAddressFlow.emit(address)
    }

    override fun getAddressFlow(): SharedFlow<Address?> {
        return activeAddressFlow
    }
}

internal val CAFES = listOf(
    Cafe(
        id = 1,
        address = "test address",
        workStartTime = LocalTime.of(12, 0),
        workEndTime = LocalTime.of(18, 0),
        contactNumber = "1234567890",
        latitude = 55.753544,
        longitude = 37.621202
    ),
    Cafe(
        id = 2,
        address = "test address 2",
        workStartTime = LocalTime.of(11, 0),
        workEndTime = LocalTime.of(19, 30),
        contactNumber = "1111111111",
        latitude = 55.755544,
        longitude = 37.625202
    )
)