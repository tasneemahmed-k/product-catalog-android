package com.example.product_catalog_android.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.ProductRepository
import com.example.data.result.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ProductDetailsUiState>(
            ProductDetailsUiState.Loading
        )

    val uiState: StateFlow<ProductDetailsUiState> =
        _uiState.asStateFlow()

    fun loadProduct(productId: Int) {

        if (productId <= 0) {
            _uiState.value = ProductDetailsUiState.Empty
            return
        }

        viewModelScope.launch {

            _uiState.value = ProductDetailsUiState.Loading

            when (val result = repository.getProductById(productId)) {

                is DataResult.Success -> {
                    _uiState.value =
                        ProductDetailsUiState.Success(result.data)
                }

                is DataResult.Error -> {
                    _uiState.value =
                        ProductDetailsUiState.Error(
                            result.exception.toString() ?: "Something went wrong."
                        )
                }
            }

        }
    }
}
