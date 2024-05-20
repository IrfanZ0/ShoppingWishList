package com.tritongames.shoppingwishlist.data.models.checkout

data class StripePaymentIntent(
    var amount: String,
    val currency: String,
    val description: String,
    val email: String,

)