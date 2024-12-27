package com.artemObrazumov.drinkin.address.domain.usecase

import com.artemObrazumov.drinkin.address.domain.data_source.CafeAddressDataSource
import com.artemObrazumov.drinkin.address.domain.models.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

class GetAddressFlowUseCase(
    private val cafeAddressDataSource: CafeAddressDataSource
) {

    operator fun invoke(): Flow<Address?> {
        return cafeAddressDataSource.getAddressFlow()
    }
}