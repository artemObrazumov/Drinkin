package com.artemObrazumov.drinkin.account.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.account.domain.usecase.LoginUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.LoginUseCaseResult
import com.artemObrazumov.drinkin.account.domain.utls.LoginFormValidationResult
import com.artemObrazumov.drinkin.account.domain.utls.LoginFormValidator
import com.artemObrazumov.drinkin.core.data.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginFormValidator: LoginFormValidator
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LoginScreenState()
        )

    fun doLogin() {
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
            val result = loginUseCase.invoke(
                login = _state.value.login,
                password = _state.value.password
            )
            when (result) {
                is LoginUseCaseResult.Failure -> {
                    _state.update {
                        _state.value.copy(
                            isLoading = false,
                            // TODO: errors
                            error = Resource.StringResource(R.string.unknown_error)
                        )
                    }
                }

                LoginUseCaseResult.Success -> {
                    _state.update {
                        _state.value.copy(
                            isLoading = false,
                            finishedLogin = true
                        )
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val result = loginFormValidator.validate(
            login = _state.value.login,
            password = _state.value.password
        )
        val error = when (result) {
            LoginFormValidationResult.LoginTooShort -> Resource.StringResource(R.string.login_too_short)
            LoginFormValidationResult.LoginTooLong -> Resource.StringResource(R.string.login_too_long)
            LoginFormValidationResult.PasswordTooShort -> Resource.StringResource(R.string.password_too_short)
            LoginFormValidationResult.PasswordTooLong -> Resource.StringResource(R.string.password_too_long)
            LoginFormValidationResult.Correct -> null
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

    fun togglePassword() {
        viewModelScope.launch {
            _state.update {
                val isVisible = _state.value.isPasswordVisible
                _state.value.copy(isPasswordVisible = !isVisible)
            }
        }
    }
}