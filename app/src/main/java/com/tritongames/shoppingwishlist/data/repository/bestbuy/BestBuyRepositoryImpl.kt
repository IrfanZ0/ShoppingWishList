package com.tritongames.shoppingwishlist.data.repository.bestbuy

import android.util.Log
import com.tritongames.shoppingwishlist.BuildConfig
import com.tritongames.shoppingwishlist.data.models.bestbuy.BestBuyAPI
import com.tritongames.shoppingwishlist.data.models.bestbuy.BestBuyInterface
import com.tritongames.shoppingwishlist.util.Resource
import retrofit2.http.Query
import javax.inject.Inject

class BestBuyRepositoryImpl @Inject constructor(private val bbInterface: BestBuyInterface) : BestBuyRepository{
    val bbAPIKey = BuildConfig.BEST_BUY_API_KEY
    override suspend fun getBestBuyPS4Products(@Query("bestBuyAPIKey") BEST_BUY_API_KEY: String): Resource<List<BestBuyAPI>> {
        return try{
            val response = bbInterface.getBestBuyPS4Products(bbAPIKey)
            val result = response.body()
            Log.d("Best Buy Repository Impl", result.toString())

            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }
            else{
                Resource.Error(response.message())
            }

        }catch (e: Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}