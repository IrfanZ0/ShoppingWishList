package com.tritongames.shoppingwishlist.data.repository.catalog

import com.tritongames.shoppingwishlist.data.models.catalog.CatalogProducts
import com.tritongames.shoppingwishlist.util.Resource
import retrofit2.http.GET

interface ProductsCatalogRepository {
    @GET("stripe/catalog")
    suspend fun getAllProducts() : Resource<List<CatalogProducts>>
}