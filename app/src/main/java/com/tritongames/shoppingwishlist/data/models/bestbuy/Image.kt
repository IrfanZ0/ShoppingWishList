package com.tritongames.shoppingwishlist.data.models.bestbuy


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("height")
    val height: String,
    @SerializedName("href")
    val href: String,
    @SerializedName("primary")
    val primary: Boolean,
    @SerializedName("rel")
    val rel: String,
    @SerializedName("unitOfMeasure")
    val unitOfMeasure: String,
    @SerializedName("width")
    val width: String
)