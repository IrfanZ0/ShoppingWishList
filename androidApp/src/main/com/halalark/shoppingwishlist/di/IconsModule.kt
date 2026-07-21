package com.tritongames.shoppingwishlist.di

import com.tritongames.shoppingwishlist.data.repository.icons.IconApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val Icons_Base_URL = "https://www.androidparadise.site/"


object IconsModule {


    val iconApi: IconApi by lazy{
        Retrofit.Builder()
            .baseUrl(Icons_Base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IconApi::class.java)
    }

}