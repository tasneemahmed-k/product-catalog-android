package com.example.data.di

import com.example.data.remote.api.ProductApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ProductApiService> {
        get<Retrofit>().create(ProductApiService::class.java)
    }
}