package com.tritongames.shopwishlist.data.storesapi

import retrofit2.http.GET

interface BestBuyApi {

    @GET("/v1/stores?format=json&show=city,longName&pageSize=2&apiKey=BEST_BUY_API_KEY")
    suspend fun getProductUPCByProductName(productName: String)

    @GET("/v1/stores?format=json&show=city,longName&pageSize=2&apiKey=BEST_BUY_API_KEY")
    suspend fun getProductAvailabilityByProductUPC(productUPC: Long)

    @GET("/v1/stores?format=json&show=city,longName&pageSize=2&apiKey=BEST_BUY_API_KEY")
    suspend fun getStoreLocation(productName: String)


}