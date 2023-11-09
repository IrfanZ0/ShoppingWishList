package com.tritongames.shoppingwishlist.data.models.categories

import retrofit2.Response
import retrofit2.http.GET

interface ShoppingCategoriesInterface {



    @GET("/IrfanZ0/Shopping-Wish-List/blob/master/ShoppingWishList/src/com/shop/shoppingwishlist/ShopCategories")
    suspend fun getAllCategoriesIcons() : Response<List<ShoppingCategoriesDataClass>>

    @GET("/shop-categories/")
    suspend fun getClothesIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getComputersIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getDiningIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getEbookIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getMoviesIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getPetsIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getTravelIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getGamesIcon() : Response<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getNoCategoryIcon() : Response<ShoppingCategoriesDataClass>



}