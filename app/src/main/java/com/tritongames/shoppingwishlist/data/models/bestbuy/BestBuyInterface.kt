package com.tritongames.shoppingwishlist.data.models.bestbuy

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BestBuyInterface {
    @GET("/v1/products((categoryPath.id=pcmcat295700050012))?&format=json")
    suspend fun getBestBuyGamingProducts(@Query("bestBuyAPIKey") BEST_BUY_API_KEY: String) : Response<List<BestBuyAPI>>

    @GET("/v1/products((categoryPath.id=pcmcat295700050012))&format=json")
    suspend fun getStores(@Query("bestBuyApiKey") BEST_BUY_API_KEY: String) : Response<List<StoresInfo>>
}
