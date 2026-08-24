package com.example.product_catalog_android.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.model.Product
import com.example.product_catalog_android.R
import com.example.product_catalog_android.ui.components.ColorSelector
import com.example.product_catalog_android.ui.components.QuantitySelector


@Composable
fun ProductDetailsScreen(
    uiState: ProductDetailsUiState
) {
    when (uiState) {

        ProductDetailsUiState.Loading -> {
            LoadingContent()
        }

        is ProductDetailsUiState.Success -> {
            ProductDetailsContent(
                product = uiState.product
            )
        }

        ProductDetailsUiState.Empty -> {
            EmptyContent()
        }

        is ProductDetailsUiState.Error -> {
            ErrorContent(
                message = uiState.message
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text("Product not available.")
    }
}

@Composable
private fun ErrorContent(
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
private fun ProductDetailsContent(
    product: Product
) {
    var selectedColor by remember {
        mutableStateOf(Color.Black)
    }

    var quantity by remember {
        mutableIntStateOf(1)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.images[0],
                contentDescription = product.title,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.category.uppercase(),
                    color = colorResource(id = R.color.gray),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = colorResource(id = R.color.yellow)
                    )

                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "(${product.reviews.size} reviews)",
                        color = colorResource(id = R.color.gray),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }


            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "$${product.price}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "DESCRIPTION",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = product.description,
                color = colorResource(id = R.color.gray),
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodyLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ColorSelector(
                    selectedColor = selectedColor,
                    onColorSelected = { color ->
                        selectedColor = color
                    }
                )

                QuantitySelector(
                    quantity = quantity,
                    onQuantityChange = { newQuantity ->
                        quantity = newQuantity
                    }
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colorResource(id = R.color.light_gray))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .weight(3f)
                        .height(64.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 8.dp, vertical = 16.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add to cart",
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = "Add To Cart",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

        }
    }
}