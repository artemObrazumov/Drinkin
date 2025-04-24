package com.artemObrazumov.drinkin.account.domain.utls

import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.LOGIN_MAX_LENGTH
import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.LOGIN_MIN_LENGTH
import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.PASSWORD_MAX_LENGTH
import com.artemObrazumov.drinkin.account.domain.utls.FormConstants.PASSWORD_MIN_LENGTH

class RegistrationFormValidator {

    fun validate(
        login: String,
        password: String,
        passwordRepeat: String
    ): RegistrationFormValidationResult {
        return if (login.length < LOGIN_MIN_LENGTH) {
            RegistrationFormValidationResult.LoginTooShort
        } else if (login.length > LOGIN_MAX_LENGTH) {
            RegistrationFormValidationResult.LoginTooLong
        } else if (password.length < PASSWORD_MIN_LENGTH) {
            RegistrationFormValidationResult.PasswordTooShort
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            RegistrationFormValidationResult.PasswordTooLong
        } else if (password != passwordRepeat) {
            RegistrationFormValidationResult.PasswordsDifferent
        } else {
            RegistrationFormValidationResult.Correct
        }
    }
}

sealed class RegistrationFormValidationResult {

    data object LoginTooShort: RegistrationFormValidationResult()
    data object LoginTooLong: RegistrationFormValidationResult()
    data object PasswordTooShort: RegistrationFormValidationResult()
    data object PasswordTooLong: RegistrationFormValidationResult()
    data object PasswordsDifferent: RegistrationFormValidationResult()

    data object Correct: RegistrationFormValidationResult()
}