package com.example.product_catalog_android.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductRepository
import com.example.data.result.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductsUiState>(
        ProductsUiState.Loading
    )

    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading

            when (val result = repository.getProducts()) {

                is DataResult.Success -> {
                    if (result.data.isEmpty()) {
                        _uiState.value = ProductsUiState.Empty
                    } else {
                        _uiState.value =
                            ProductsUiState.Success(result.data)
                    }
                }

                is DataResult.Error -> {
                    _uiState.value = ProductsUiState.Error(
                        message = result.exception.message
                            ?: "Unable to load products."
                    )
                }
            }
        }
    }

    fun refreshProducts() {
        loadProducts()
    }
}