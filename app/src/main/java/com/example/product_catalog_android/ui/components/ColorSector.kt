package com.example.product_catalog_android.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.product_catalog_android.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ColorSelector(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "COLOR",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val colors = listOf(
                Color.Black,
                Color.Gray,
                colorResource(id = R.color.beige)
            )

            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selectedColor == color) 2.dp else 0.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                        .clickable {
                            onColorSelected(color)
                        }
                )
            }
        }
    }
}