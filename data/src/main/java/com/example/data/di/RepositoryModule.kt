package com.example.data.di

import com.example.data.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        ProductRepository(get())
    }
}