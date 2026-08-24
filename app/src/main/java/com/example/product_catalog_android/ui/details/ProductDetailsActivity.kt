package com.example.product_catalog_android.ui.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsActivity : ComponentActivity() {

    companion object {
        const val EXTRA_PRODUCT_ID = "product_id"
    }

    private val viewModel: ProductDetailsViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getIntExtra(EXTRA_PRODUCT_ID, -1)

        viewModel.loadProduct(productId)

        setContent {
            val uiState =
                viewModel.uiState.collectAsStateWithLifecycle()

            ProductDetailsScreen(
                uiState = uiState.value, onBackClick = {
                    finish()
                }
            )
        }
    }
}