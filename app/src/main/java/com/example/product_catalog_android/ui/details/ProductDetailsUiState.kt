package com.example.product_catalog_android.ui.details

import com.example.data.model.Product

sealed interface ProductDetailsUiState {
    data object Loading : ProductDetailsUiState

    data class Success(
        val product: Product
    ) : ProductDetailsUiState

    data object Empty : ProductDetailsUiState

    data class Error(
        val message: String
    ) : ProductDetailsUiState
}