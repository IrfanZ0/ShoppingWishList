package com.tritongames.shoppingwishlist.data.repository.checkout

import com.tritongames.shoppingwishlist.data.models.checkout.StripePaymentIntent
import com.tritongames.shoppingwishlist.util.Resource
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface CheckoutRepository {
    @GET("stripe/payments/customerpayments.php")
    suspend fun getPaymentIntent(id : String) : Resource<StripePaymentIntent>

    @GET("stripe/payments/customerpayments.php")
    suspend fun getAllPaymentIntents() : Resource<List<StripePaymentIntent>>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("stripe/payments/customerpayments.php")
    suspend fun createPaymentIntent(@Body stripeIntentJson: StripePaymentIntent) : Resource<StripePaymentIntent>

    @POST("stripe/payments/customerpayments.php")
    suspend fun updatePaymentIntent(id : String) : Resource<StripePaymentIntent>

    @POST("stripe/payments/customerpayments.php")
    suspend fun confirmPaymentIntent(id : String) : Resource<StripePaymentIntent>

    @POST("stripe/payments/customerpayments.php")
    suspend fun cancelPaymentIntent(id : String) : Resource<StripePaymentIntent>

}