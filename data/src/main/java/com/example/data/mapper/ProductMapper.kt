package com.example.data.mapper

import com.example.data.model.Product
import com.example.data.model.Rating
import com.example.data.remote.dto.ProductDto


fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = Rating(
            rate = rating.rate,
            count = rating.count
        )
    )
}