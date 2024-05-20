package com.tritongames.shoppingwishlist.data.models.bestbuy


import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("ground")
    val ground: String,
    @SerializedName("nextDay")
    val nextDay: String,
    @SerializedName("secondDay")
    val secondDay: String,
    @SerializedName("vendorDelivery")
    val vendorDelivery: String
)