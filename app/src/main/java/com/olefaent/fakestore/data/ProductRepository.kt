package com.olefaent.fakestore.data

import com.olefaent.fakestore.model.Product
import com.olefaent.fakestore.network.ProductApiService

interface ProductRepository{
    /**
     * Gets a list of products from the network
     */
    suspend fun getProducts(): List<Product>
}

class NetworkProductRepository(
    private val productApiService: ProductApiService
): ProductRepository {
    override suspend fun getProducts(): List<Product> = productApiService.getProducts()
}