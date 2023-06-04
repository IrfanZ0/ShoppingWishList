package com.tritongames.shopwishlist.data.storesapi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object BestBuyRetrofitInstance {
    @Provides
    fun ProvidesBestBuyAPI(): BestBuyApi{

        return Retrofit.Builder()
        .baseUrl("https://api.bestbuy.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BestBuyApi::class.java)
    }

}