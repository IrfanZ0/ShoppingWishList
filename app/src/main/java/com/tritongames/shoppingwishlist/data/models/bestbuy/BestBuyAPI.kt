package com.tritongames.shoppingwishlist.data.models.bestbuy


import com.google.gson.annotations.SerializedName

data class BestBuyAPI(
    @SerializedName("canonicalUrl")
    val canonicalUrl: String,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("from")
    val from: Int,
    @SerializedName("partial")
    val partial: Boolean,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("queryTime")
    val queryTime: String,
    @SerializedName("to")
    val to: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("totalTime")
    val totalTime: String
)