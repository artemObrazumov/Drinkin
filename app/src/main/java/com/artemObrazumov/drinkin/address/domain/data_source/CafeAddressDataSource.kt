package com.artemObrazumov.drinkin.address.domain.data_source

import com.artemObrazumov.drinkin.address.domain.models.Address
import com.artemObrazumov.drinkin.address.domain.models.Cafe
import com.artemObrazumov.drinkin.address.presentation.models.CafeUi
import com.artemObrazumov.drinkin.core.domain.util.NetworkError
import com.artemObrazumov.drinkin.core.domain.util.Result
import kotlinx.coroutines.flow.SharedFlow

interface CafeAddressDataSource {
    suspend fun getCafes(): Result<List<Cafe>, NetworkError>
    suspend fun updateActiveAddress(address: Address)
    fun getAddressFlow(): SharedFlow<Address?>
}
