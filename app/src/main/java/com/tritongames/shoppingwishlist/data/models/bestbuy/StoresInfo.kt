package com.tritongames.shoppingwishlist.data.models.bestbuy

data class StoresInfo(
    val currentPage: Int,
    val from: Int,
    val stores: List<Store>,
    val product : List<Product>,
    val to: Int,
    val total: Int,
    val totalPages: Int
)