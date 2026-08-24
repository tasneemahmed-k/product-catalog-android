package com.example.data.mapper

import com.example.data.model.Product
import com.example.data.model.Review
import com.example.data.remote.dto.ProductDto
import com.example.data.remote.dto.ReviewDto

//Extension function
fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        stock = stock,
        description = description,
        category = category,
        images = images,
        rating = rating,
        reviews = reviews.map { it.toReview() }
    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        rating = rating,
        reviewerName = reviewerName
    )
}