package com.artemObrazumov.drinkin.address.domain.usecase

import com.artemObrazumov.drinkin.address.domain.data_source.CafeAddressDataSource
import com.artemObrazumov.drinkin.address.domain.models.Address

class ChangeAddressUseCase(
    private val dataSource: CafeAddressDataSource
) {

    suspend operator fun invoke(address: Address) {
        dataSource.updateActiveAddress(address)
    }
}