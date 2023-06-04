package com.tritongames.shopwishlist.util

import retrofit2.Call
import retrofit2.http.*


interface LoginService {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    fun getAccessToken(
        @Field("code") code: String?,
        @Field("redirect_uri") reDirectUri: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?

    ): Call<AccessToken?>
}