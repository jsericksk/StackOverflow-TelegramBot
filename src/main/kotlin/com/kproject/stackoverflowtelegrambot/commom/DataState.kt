package com.kproject.stackoverflowtelegrambot.commom

sealed class DataState<out T>(val result: T? = null) {
    data class Success<T>(val data: T? = null) : DataState<T>(result = data)
    data class Error<T>(val message: String) : DataState<T>()
}