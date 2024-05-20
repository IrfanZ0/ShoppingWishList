package com.tritongames.shoppingwishlist.data.repository.checkout

import com.tritongames.shoppingwishlist.BuildConfig
import com.tritongames.shoppingwishlist.data.models.checkout.StripeInterface
import com.tritongames.shoppingwishlist.data.models.checkout.StripePaymentIntent
import com.tritongames.shoppingwishlist.util.Resource
import javax.inject.Inject

class DefaultCheckoutRepository @Inject constructor(private val apiCheckout : StripeInterface): CheckoutRepository {
   val apiStripeKey = BuildConfig.STRIPE_API_KEY

    override suspend fun getPaymentIntent(id: String): Resource<StripePaymentIntent> {
        return try {
            val response = apiCheckout.getPaymentIntent(id)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error ("Unknown Error")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown Error")

        }

    }

    override suspend fun getAllPaymentIntents(): Resource<List<StripePaymentIntent>> {
        return try {
            val response = apiCheckout.getAllPaymentIntents()
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error ("Unknown Error")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown Error")

        }

    }

    override suspend fun createPaymentIntent(stripePaymentIntent: StripePaymentIntent): Resource<StripePaymentIntent> {
        return try {
            val response = apiCheckout.createPaymentIntent(stripePaymentIntent)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error ("Unknown Error")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown Error")

        }
    }

    override suspend fun updatePaymentIntent(id: String): Resource<StripePaymentIntent> {

        return try {
            val response = apiCheckout.updatePaymentIntent(id)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error ("Unknown Error")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown Error")

        }
    }

    override suspend fun confirmPaymentIntent(id: String): Resource<StripePaymentIntent> {

        return try {
            val response = apiCheckout.confirmPaymentIntent(id)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error ("Unknown Error")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown Error")

        }
    }

    override suspend fun cancelPaymentIntent(id: String): Resource<StripePaymentIntent> {

        return try {
            val response = apiCheckout.cancelPaymentIntent(id)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error ("Unknown Error")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown Error")

        }
    }
}