package com.tritongames.shoppingwishlist.data.repository.catalog

import com.tritongames.shoppingwishlist.data.models.catalog.CatalogInterface
import com.tritongames.shoppingwishlist.data.models.catalog.CatalogProducts
import com.tritongames.shoppingwishlist.util.Resource
import javax.inject.Inject

class ProductsCatalogRepositoryImpl @Inject constructor(private val prodCatalogRepo: CatalogInterface): ProductsCatalogRepository {
    override suspend fun getAllProducts(): Resource<List<CatalogProducts>> {
       return try {
           val response = prodCatalogRepo.getAllProducts()
           val result = response.body()

           if (response.isSuccessful && result != null){
               Resource.Success (result)
           }
           else{
               Resource.Error (response.message())

           }

       }catch (e : Exception){
          Resource.Error(e.message ?: "An error occurred")
       }
    }
}