package com.tritongames.shoppingwishlist.data.models.categories

import com.tritongames.shoppingwishlist.data.models.Store

data class ShoppingCategoriesDataClass(
    val category: String,
    val image: Int,
    val stores: List<Store>

)