package com.example.product_catalog_android.ui.common

import com.example.data.result.DataError

fun getErrorMessage(error: DataError): String {
    return when (error) {

        DataError.NoInternet ->
            "No internet connection. Please check your connection."

        is DataError.ServerError ->
            "The server is currently unavailable. Please try again later."

        DataError.SerializationError ->
            "We couldn't read the product information."

        DataError.EmptyResponse ->
            "No products are available right now."

        DataError.ProductNotFound ->
            "The product could not be found."

        DataError.InvalidProduct ->
            "The product information is invalid."

        DataError.Unknown ->
            "Something went wrong. Please try again."
    }
}