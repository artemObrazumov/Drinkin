package com.artemObrazumov.drinkin.address.presentation.addressSelect

import androidx.compose.runtime.Immutable
import com.artemObrazumov.drinkin.address.presentation.models.CafeUi
import com.artemObrazumov.drinkin.core.domain.util.Error

@Immutable
sealed class AddressSelectScreenState {
    data object Loading : AddressSelectScreenState()
    data class Content(
        val cafes: List<CafeUi> = emptyList(),
        val isCafeOpened: Boolean = false,
        val selectedCafe: CafeUi? = null
    ): AddressSelectScreenState()
    data class Failure(
        val error: Error
    ): AddressSelectScreenState()
}
