package com.artemObrazumov.drinkin.account.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.account.domain.usecase.RegisterUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.RegisterUseCaseResult
import com.artemObrazumov.drinkin.account.domain.utls.RegistrationFormValidationResult
import com.artemObrazumov.drinkin.account.domain.utls.RegistrationFormValidator
import com.artemObrazumov.drinkin.core.data.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationScreenViewModel(
    private val registerUseCase: RegisterUseCase,
    private val registrationFormValidator: RegistrationFormValidator
): ViewModel() {

    private val _state = MutableStateFlow(RegistrationScreenState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            RegistrationScreenState()
        )

    fun doRegistration() {
        viewModelScope.launch {
            if (!validateForm()) {
                return@launch
            }
            _state.update {
                _state.value.copy(
                    isLoading = true,
                    error = null
                )
            }
            delay(5000)
            val result = registerUseCase.invoke(
                login = _state.value.login,
                password = _state.value.password
            )
            when (result) {
                is RegisterUseCaseResult.Failure -> {
                    _state.update {
                        _state.value.copy(
                            isLoading = false,
                            // TODO: errors
                            error = Resource.StringResource(R.string.unknown_error)
                        )
                    }
                }

                RegisterUseCaseResult.Success -> {
                    _state.update {
                        _state.value.copy(
                            isLoading = false,
                            finishedRegistration = true
                        )
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val result = registrationFormValidator.validate(
            login = _state.value.login,
            password = _state.value.password,
            passwordRepeat = _state.value.passwordRepeat
        )
        val error = when (result) {
            RegistrationFormValidationResult.LoginTooShort -> Resource.StringResource(R.string.login_too_short)
            RegistrationFormValidationResult.LoginTooLong -> Resource.StringResource(R.string.login_too_long)
            RegistrationFormValidationResult.PasswordTooShort -> Resource.StringResource(R.string.password_too_short)
            RegistrationFormValidationResult.PasswordTooLong -> Resource.StringResource(R.string.password_too_long)
            RegistrationFormValidationResult.PasswordsDifferent -> Resource.StringResource(R.string.passwords_different)
            RegistrationFormValidationResult.Correct -> null
        }
        if (error == null) {
            return true
        } else {
            _state.update {
                _state.value.copy(
                    error = error
                )
            }
            return false
        }
    }

    fun updateLogin(login: String) {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(login = login)
            }
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(password = password)
            }
        }
    }

    fun updatePasswordRepeat(passwordRepeat: String) {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(passwordRepeat = passwordRepeat)
            }
        }
    }

    fun togglePassword() {
        viewModelScope.launch {
            _state.update {
                val isVisible = _state.value.isPasswordVisible
                _state.value.copy(isPasswordVisible = !isVisible)
            }
        }
    }
}