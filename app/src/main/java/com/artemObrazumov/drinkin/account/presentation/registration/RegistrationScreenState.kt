package com.artemObrazumov.drinkin.account.presentation.registration

import com.artemObrazumov.drinkin.core.data.Resource

data class RegistrationScreenState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val isPasswordVisible: Boolean = false,
    val error: Resource.StringResource? = null,
    val finishedRegistration: Boolean = false
)
