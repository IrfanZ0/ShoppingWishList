package com.tritongames.shoppingwishlist.data.models.contacts

import com.google.gson.annotations.SerializedName

data class ContactResponseItem(
    val comment: String?,
    @SerializedName("data")
    val dataList: List<Data?>,
    val database: String?,
    val name: String?,
    val type: String?,
    val version: String?
)