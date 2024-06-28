package com.tritongames.shoppingwishlist.data.models.contacts

import com.google.gson.annotations.SerializedName

data class Address(
    val City: String,
    val State: String,
    @SerializedName("Street Address")
    val Street_Address: String,
    val Zip: String
)