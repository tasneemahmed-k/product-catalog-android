package com.example.data.remote.api

import com.example.data.remote.dto.ProductDto
import com.example.data.remote.dto.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto
}