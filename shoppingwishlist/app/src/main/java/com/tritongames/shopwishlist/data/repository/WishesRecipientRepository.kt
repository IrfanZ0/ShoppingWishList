package com.tritongames.shopwishlist.data.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WishesRecipientRepository {
    @GET("/shopper-data")
    suspend fun getAllWishes() : Response<List<WishesResponse>>

    @GET("/shopper-data")
    suspend fun getFullNames(@Path("first_name") fNameList: MutableList<String>?, @Path("last_name") lNameList: MutableList<String>?) : Response<MutableList<WishesResponse>>

    @GET("/shopper-data")
    suspend fun getFirstName(@Path("first_name") fName : String) : Response<WishesResponse>

    @GET("/shopper-data")
    suspend fun getLastName(@Path("last_name") lName : String) : Response<WishesResponse>

    @GET("/shopper-data")
    suspend fun getFirstWish(@Path("wish1") wish1 : String) : Response<WishesResponse>

    @GET("/shopper-data")
    suspend fun getSecondWish(@Path("wish2") wish2 : String) : Response<WishesResponse>

    @GET("/shopper-data")
    suspend fun getThirdWish(@Path("wish3") wish3 : String) : Response<WishesResponse>

    /*@POST("/shopper-data")
    suspend fun setFirstName (
        @Body fName: String,
    )

    @POST()
    suspend fun setLastName (
        @Body lName: String,
    )

    @POST()
    suspend fun setFirstWish (
        @Body firstWish: String,
    )

    @POST()
    suspend fun setSecondWish (
        @Body wishes: String,
    )

    @POST()
    suspend fun setThirdWish (
        @Body wishes: String,
    )
*/
}