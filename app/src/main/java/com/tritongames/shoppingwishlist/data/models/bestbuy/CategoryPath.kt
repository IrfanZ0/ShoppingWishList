package com.tritongames.shoppingwishlist.data.models.bestbuy


import com.google.gson.annotations.SerializedName

data class CategoryPath(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)