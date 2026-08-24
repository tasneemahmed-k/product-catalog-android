package com.example.product_catalog_android.ui.products

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.product_catalog_android.ui.details.ProductDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsActivity : ComponentActivity() {
    private val viewModel: ProductsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            uiState is State Flow and compose needs to collect that Flow and react whenever it changes
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val searchText by viewModel.searchText.collectAsState()

            ProductsScreen(
                uiState.value,
                onProductClick = { productId ->
                    val intent = Intent(
                        this,
                        ProductDetailsActivity::class.java
                    ).apply {
                        putExtra(ProductDetailsActivity.EXTRA_PRODUCT_ID, productId)
                    }

                    startActivity(intent)
                },
                onRefresh = viewModel::refreshProducts,
                searchText = searchText,
                onSearchTextChanged = viewModel::onSearchTextChanged,
            )
        }
    }
}
