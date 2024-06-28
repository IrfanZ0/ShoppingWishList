package com.tritongames.shoppingwishlist.data.repository.checkout

import com.tritongames.shoppingwishlist.data.models.checkout.StripeCustomerData
import com.tritongames.shoppingwishlist.util.Resource
import retrofit2.http.GET

interface CustomerCheckoutRepository {
    @GET("/stripe/")
    suspend fun getCustomerPurchaseInfo() : Resource<StripeCustomerData>
}