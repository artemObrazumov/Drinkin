package com.artemObrazumov.drinkin.account.presentation.login

data class LoginScreenState(
    val loading: Boolean = false,
    val login: String = "",
    val password: String = ""
)
