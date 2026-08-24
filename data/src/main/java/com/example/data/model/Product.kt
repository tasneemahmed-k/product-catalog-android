package com.example.data.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val stock: Int,
    val description: String,
    val category: String,
    val images: List<String>,
    val rating: Double,
    val reviews: List<Review>
)

data class Review(
    val rating: Int,
    val reviewerName: String
)