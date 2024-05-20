package com.tritongames.shoppingwishlist.data.models.checkout

data class StripeCustomerData(
    val customer: String,
    val ephemeralKey: String,
    val paymentIntent: String,
    val publishableKey: String
)