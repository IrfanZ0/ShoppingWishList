package com.tritongames.shoppingwishlist.data.models.checkout

import retrofit2.Response
import retrofit2.http.GET

interface StripeCustomerApi {
    @GET("/stripe/")
    suspend fun getCustomerPurchaseInfo() : Response<StripeCustomerData>



}