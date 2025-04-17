package com.artemObrazumov.drinkin.account.presentation.registration

data class RegistrationScreenState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val error: String? = null
)
