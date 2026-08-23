package com.example.product_catalog_android.ui.products

import com.example.data.model.Product

sealed interface ProductsUiState {
    data object Loading : ProductsUiState

    data class Success(
        val products: List<Product>
    ) : ProductsUiState

    data object Empty : ProductsUiState

    data class Error(
        val message: String
    ) : ProductsUiState
}