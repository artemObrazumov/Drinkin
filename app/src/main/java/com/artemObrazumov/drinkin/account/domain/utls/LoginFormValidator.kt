package com.artemObrazumov.drinkin.account.domain.utls

import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.LOGIN_MAX_LENGTH
import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.LOGIN_MIN_LENGTH
import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.PASSWORD_MAX_LENGTH
import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.PASSWORD_MIN_LENGTH

class LoginFormValidator {

    fun validate(
        login: String,
        password: String
    ): LoginFormValidationResult {
        return if (login.length < LOGIN_MIN_LENGTH) {
            LoginFormValidationResult.LoginTooShort
        } else if (login.length > LOGIN_MAX_LENGTH) {
            LoginFormValidationResult.LoginTooLong
        } else if (password.length < PASSWORD_MIN_LENGTH) {
            LoginFormValidationResult.PasswordTooShort
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            LoginFormValidationResult.PasswordTooLong
        } else {
            LoginFormValidationResult.Correct
        }
    }
}

sealed class LoginFormValidationResult {

    data object LoginTooShort: LoginFormValidationResult()
    data object LoginTooLong: LoginFormValidationResult()
    data object PasswordTooShort: LoginFormValidationResult()
    data object PasswordTooLong: LoginFormValidationResult()

    data object Correct: LoginFormValidationResult()
}