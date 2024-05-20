package com.tritongames.shoppingwishlist.data.models.checkout

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface StripeInterface {
    @GET("stripe/payments/customerpayments.php/")
    suspend fun getPaymentIntent(id : String) : Response<StripePaymentIntent>

    @GET("stripe/payments/customerpayments.php/")
    suspend fun getAllPaymentIntents() : Response<List<StripePaymentIntent>>

    @Headers("Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("stripe/payments/customerpayments.php")
    suspend fun createPaymentIntent(@Body stripeIntentJson: StripePaymentIntent) : Response<StripePaymentIntent>

    @POST("stripe/payments/customerpayments.php")
    suspend fun updatePaymentIntent(id : String) : Response<StripePaymentIntent>

    @POST("stripe/payments/customerpayments.php")
    suspend fun confirmPaymentIntent(id : String) : Response<StripePaymentIntent>

    @POST("stripe/payments/customerpayments.php")
    suspend fun cancelPaymentIntent(id : String) : Response<StripePaymentIntent>




}