package com.example.data.repository

import com.example.data.mapper.toProduct
import com.example.data.model.Product
import com.example.data.remote.api.ProductApiService
import com.example.data.result.DataResult

class ProductRepository(private val apiService: ProductApiService) {

    suspend fun getProducts(): DataResult<List<Product>> {
        return try {
            val response = apiService.getProducts()
            val products = response.products
            if (products.isEmpty())
                DataResult.Success(emptyList())
            else
                DataResult.Success(products.map { it.toProduct() })
        } catch (exception: Exception) {
            DataResult.Error(exception)
        }
    }

    suspend fun getProductById(id: Int): DataResult<Product> {
        return try {
            val product = apiService.getProductById(id)
            DataResult.Success(product.toProduct())
        } catch (exception: Exception) {
            DataResult.Error(exception)
        }
    }
}