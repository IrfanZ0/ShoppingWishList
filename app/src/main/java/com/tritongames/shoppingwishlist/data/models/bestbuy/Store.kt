package com.tritongames.shoppingwishlist.data.models.bestbuy

data class Store(
    val address: String,
    val city: String,
    val lat: Double,
    val lng: Double,
    val name: String,
    val region: String,
    val storeId: Int,
    val storeType: String,
    val zip: String
)