package com.tritongames.shoppingwishlist.data.models

import com.google.gson.annotations.SerializedName

data class Wishes(
    @SerializedName("wish 1")
    val wish1: String,
    @SerializedName("wish 2")
    val wish2: String,
    @SerializedName("wish 3")
    val wish3: String
)