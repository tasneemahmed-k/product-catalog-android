package com.example.data.result

sealed class DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>()

    data class Error(val exception: Throwable) : DataResult<Nothing>()
}