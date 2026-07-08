package com.tritongames.shoppingwishlist.data.repository.icons

import com.tritongames.shoppingwishlist.data.models.Icons
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IconApi {

    @GET("/restful/icons.php")
    suspend fun getIcons(): Response<Icons>

    @POST("/restful/icons.php")
    suspend fun postIcons(@Body newIcon:Icons): Response<Icons>
}