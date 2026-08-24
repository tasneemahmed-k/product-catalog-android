package com.example.data.repository

import com.example.data.mapper.toProduct
import com.example.data.model.Product
import com.example.data.remote.api.ProductApiService
import com.example.data.result.DataError
import com.example.data.result.DataResult
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException

class ProductRepository(private val apiService: ProductApiService) {

    suspend fun getProducts(): DataResult<List<Product>> {
//        return DataResult.Error(
//            DataError.ProductNotFound
//        )

        return try {
            val response = apiService.getProducts()
            val products = response.products
            if (products.isEmpty())
                DataResult.Error(DataError.EmptyResponse)
            else
                DataResult.Success(products.map { it.toProduct() })
        } catch (exception: IOException) {

            DataResult.Error(
                DataError.NoInternet
            )

        } catch (exception: HttpException) {

            DataResult.Error(
                DataError.ServerError(
                    code = exception.code()
                )
            )

        } catch (exception: JsonSyntaxException) {
            DataResult.Error(DataError.SerializationError)
        } catch (exception: Exception) {
            DataResult.Error(DataError.Unknown)
        }
    }

    suspend fun getProductById(id: Int): DataResult<Product> {
        return try {
            val product = apiService.getProductById(id)
            if (product.id <= 0) {
                DataResult.Error(
                    DataError.InvalidProduct
                )
            } else {
                DataResult.Success(
                    product.toProduct()
                )
            }
        } catch (exception: IOException) {

            DataResult.Error(
                DataError.NoInternet
            )

        } catch (exception: HttpException) {

            DataResult.Error(
                DataError.ServerError(
                    code = exception.code()
                )
            )

        } catch (exception: JsonSyntaxException) {
            DataResult.Error(DataError.SerializationError)
        } catch (exception: Exception) {
            DataResult.Error(DataError.Unknown)
        }
    }
}