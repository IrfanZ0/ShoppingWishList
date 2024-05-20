package com.tritongames.shoppingwishlist.data.repository.bestbuy

import com.tritongames.shoppingwishlist.data.models.bestbuy.BestBuyAPI
import com.tritongames.shoppingwishlist.data.models.bestbuy.StoresInfo
import com.tritongames.shoppingwishlist.util.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface BestBuyRepository {

    @GET("/v1/products((categoryPath.id=pcmcat295700050012))&format=json")
    suspend fun getBestBuyGamingProducts(@Query("bestBuyAPIKey") BEST_BUY_API_KEY: String) : Resource<List<BestBuyAPI>>

    @GET("/v1/products((categoryPath.id=pcmcat295700050012))&format=json")
    suspend fun getStores(@Query("bestBuyApiKey") BEST_BUY_API_KEY: String) : Resource<List<StoresInfo>>
}