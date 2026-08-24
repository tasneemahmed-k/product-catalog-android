package com.example.product_catalog_android.di

import com.example.product_catalog_android.ui.details.ProductDetailsViewModel
import com.example.product_catalog_android.ui.products.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
//        give ProductsViewModel whatever dependency it needs.
        ProductsViewModel(get())
    }

    viewModel {
        ProductDetailsViewModel(get())
    }
}