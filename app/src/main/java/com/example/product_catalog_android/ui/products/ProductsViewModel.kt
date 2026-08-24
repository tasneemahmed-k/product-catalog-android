package com.example.product_catalog_android.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Product
import com.example.data.repository.ProductRepository
import com.example.data.result.DataError
import com.example.data.result.DataResult
import com.example.product_catalog_android.ui.common.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductsUiState>(
        ProductsUiState.Loading
    )

    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    private val _searchText = MutableStateFlow("")

    val searchText: StateFlow<String> =
        _searchText.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading

            when (val result = repository.getProducts()) {

                is DataResult.Success -> {
                    allProducts = result.data
                    if (result.data.isEmpty()) {
                        _uiState.value = ProductsUiState.Empty
                    } else {
                        _uiState.value =
                            ProductsUiState.Success(result.data)
                    }
                }

                is DataResult.Error -> {
                    _uiState.value = ProductsUiState.Error(
                        message = getErrorMessage(result.exception)
                    )
                }
            }
        }
    }

    fun refreshProducts() {
        loadProducts()
    }

    fun onSearchTextChanged(text: String) {

        _searchText.value = text

        val filteredProducts = allProducts.filter { product ->

            product.title.contains(
                text,
                ignoreCase = true
            ) ||

                    product.category.contains(
                        text,
                        ignoreCase = true
                    )
        }

        _uiState.value =
            if (filteredProducts.isEmpty()) {
                ProductsUiState.Empty
            } else {
                ProductsUiState.Success(filteredProducts)
            }
    }
}