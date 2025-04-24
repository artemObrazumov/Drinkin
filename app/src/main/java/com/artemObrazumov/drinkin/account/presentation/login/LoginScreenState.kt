package com.artemObrazumov.drinkin.account.presentation.login

import com.artemObrazumov.drinkin.core.data.Resource

data class LoginScreenState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val error: Resource.StringResource? = null,
    val finishedLogin: Boolean = false
)
