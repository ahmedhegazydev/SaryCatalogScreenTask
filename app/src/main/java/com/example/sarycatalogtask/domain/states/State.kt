package com.example.sarycatalogtask.domain.states

import com.example.sarycatalogtask.domain.response.ErrorResponse


sealed class State<T> {
    class Loading<T> : State<T>()

    data class Success<T>(val data: T) : State<T>()

    data class Error<T>(val error: ErrorResponse, val message: String) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> error(error: ErrorResponse, message: String, ) = Error<T>(error, message)
    }

}