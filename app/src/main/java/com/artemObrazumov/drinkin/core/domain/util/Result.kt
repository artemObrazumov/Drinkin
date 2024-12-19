package com.artemObrazumov.drinkin.core.domain.util

typealias DomainError = Error

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.errorOrNull(): E? {
    return when(this) {
        is Result.Error -> this.error
        is Result.Success -> null
    }
}

fun <T, E: Error> Result<T, E>.contentOrNull(): T? {
    return when(this) {
        is Result.Error -> null
        is Result.Success -> this.data
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

typealias EmptyResult<E> = Result<Unit, E>