package com.example.product_catalog_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.model.Product
import com.example.product_catalog_android.R

@Composable
fun ProductItem(
    product: Product,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(product.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.images[0],
                contentDescription = product.title,
                modifier = Modifier.fillMaxWidth()
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Add to favourites", tint = Color.Black,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }


        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = product.category.toUpperCase(), color = colorResource(id = R.color.gray),
                fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = product.title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "$${product.price}",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}