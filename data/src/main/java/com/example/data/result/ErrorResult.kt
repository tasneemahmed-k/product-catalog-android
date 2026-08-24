package com.example.data.result

sealed class DataError {

    data object NoInternet : DataError()

    data class ServerError(
        val code: Int
    ) : DataError()

    data object SerializationError : DataError()

    data object EmptyResponse : DataError()

    data object ProductNotFound : DataError()

    data object InvalidProduct : DataError()

    data object Unknown : DataError()
}