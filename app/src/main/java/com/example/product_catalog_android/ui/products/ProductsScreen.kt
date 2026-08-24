package com.example.product_catalog_android.ui.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.Product
import com.example.product_catalog_android.ui.components.ProductItem
import com.example.product_catalog_android.ui.components.ProductSearchBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductsScreen(
    uiState: ProductsUiState,
    onProductClick: (Int) -> Unit,
    onRefresh: () -> Unit
) {
    val refreshState = rememberPullRefreshState(
        refreshing = uiState is ProductsUiState.Loading,
        onRefresh = onRefresh
    )

    var searchText by remember { mutableStateOf("") }

    when (uiState) {
        ProductsUiState.Loading -> {
            LoadingContent()
        }

        is ProductsUiState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshState)
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "New Arrivals",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "Discover our latest collection",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProductSearchBar(value = searchText, onValueChange = { searchText = it })

                    Spacer(modifier = Modifier.height(32.dp))

                    ProductsList(
                        products = uiState.products,
                        onProductClick,
                    )
                }
                PullRefreshIndicator(
                    refreshing = uiState is ProductsUiState.Loading,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

        ProductsUiState.Empty -> {
            EmptyContent()
        }

        is ProductsUiState.Error -> {
            ErrorContent(message = uiState.message)
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No products available.")
    }
}

@Composable
private fun ErrorContent(
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}

@Composable
private fun ProductsList(
    products: List<Product>,
    onProductClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductItem(product = product, onClick = onProductClick)
        }
    }
}