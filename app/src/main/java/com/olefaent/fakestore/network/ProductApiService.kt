package com.olefaent.fakestore.network

import com.olefaent.fakestore.model.Product
import retrofit2.http.GET

/**
 * Retrofit service object for creating api calls
 */
interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}