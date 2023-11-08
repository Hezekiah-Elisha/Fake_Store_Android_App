package com.olefaent.fakestore.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.olefaent.fakestore.network.ProductApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val productRepository : ProductRepository
}

class DefaultAppContainer : AppContainer {
    private val base_url = "https://fakestoreapi.com/"

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit =  Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val retrofitService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        NetworkProductRepository(retrofitService)
    }

}