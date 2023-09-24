package com.tritongames.shoppingwishlist.data.repository.category

import com.tritongames.shoppingwishlist.data.models.categories.ShoppingCategoriesDataClass
import com.tritongames.shoppingwishlist.util.Resource
import retrofit2.http.GET
import retrofit2.http.Header

interface ShopCategoryRepository {


    @GET("/repos/IrfanZ0/shopping-wish-list/contents/wp-json/wp/v2/shop-categories")
    suspend fun getShopCategoryRepo(@Header("Authorization") token: String)

    @GET("/shopping-wish-list/wp-json/wp/v2/shop-categories/")
    suspend fun getAllCategoriesIcons(@Header("Authorization") token: String) : Resource<List<ShoppingCategoriesDataClass>>

    @GET("/shopping-wish-list/wp-json/wp/v2/shop-categories/")
    suspend fun getClothesIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shopping-wish-list/wp-json/wp/v2/shop-categories/")
    suspend fun getComputersIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shopping-wish-list/wp-json/wp/v2/shop-categories/")
    suspend fun getDiningIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getEbookIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getMoviesIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getPetsIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getTravelIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getGamesIcon() : Resource<ShoppingCategoriesDataClass>

    @GET("/shop-categories/")
    suspend fun getNoCategoryIcon() : Resource<ShoppingCategoriesDataClass>

}