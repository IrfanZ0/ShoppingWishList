package com.tritongames.shoppingwishlist.data.models.catalog

import retrofit2.Response
import retrofit2.http.GET

interface CatalogInterface {
    @GET("stripe/catalog")
    suspend fun getAllProducts() : Response<List<CatalogProducts>>
}