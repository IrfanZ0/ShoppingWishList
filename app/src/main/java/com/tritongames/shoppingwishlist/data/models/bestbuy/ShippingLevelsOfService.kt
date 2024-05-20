package com.tritongames.shoppingwishlist.data.models.bestbuy


import com.google.gson.annotations.SerializedName

data class ShippingLevelsOfService(
    @SerializedName("serviceLevelId")
    val serviceLevelId: Int,
    @SerializedName("serviceLevelName")
    val serviceLevelName: String,
    @SerializedName("unitShippingPrice")
    val unitShippingPrice: Double
)