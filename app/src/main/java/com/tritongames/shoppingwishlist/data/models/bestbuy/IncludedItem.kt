package com.tritongames.shoppingwishlist.data.models.bestbuy


import com.google.gson.annotations.SerializedName

data class IncludedItem(
    @SerializedName("includedItem")
    val includedItem: String
)