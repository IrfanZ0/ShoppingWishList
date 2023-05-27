package com.tritongames.shopwishlist.data.repository

data class WishesResponse(
    val ID: Int,
    val first_name: String,
    val last_name: String,
    val wish1: String,
    val wish2: String,
    val wish3: String
) {
    fun submitList(value: List<WishesResponse>) {

    }

    val currentList: List<WishesResponse> = TODO()
}