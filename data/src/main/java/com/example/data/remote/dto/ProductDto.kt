package com.example.data.remote.dto

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val stock: Int,
    val description: String,
    val category: String,
    val images: List<String>,
    val rating: Double,
    val reviews: List<ReviewDto>
)

data class ProductsResponse(
    val products: List<ProductDto>
)