package com.tritongames.shopwishlist.data

import com.tritongames.shopwishlist.data.repository.WishesResponse
import retrofit2.Response
import retrofit2.http.GET

interface WishesApi {
    @GET("/shopper-data")
    fun getWishes(): Response<List<WishesResponse>>

    @GET("/shopper-data")
    fun getFirstName(fName: String): Response<WishesResponse>

    @GET("/shopper-data")
    fun getLastName(lName: String): Response<WishesResponse>

    @GET("/shopper-data")
    fun getFirstWish(first_wish: String): Response<WishesResponse>

    @GET("/shopper-data")
    fun getSecondWish(second_wish: String): Response<WishesResponse>

    @GET("/shopper-data")
    fun getThirdWish(third_wish: String): Response<WishesResponse>


}