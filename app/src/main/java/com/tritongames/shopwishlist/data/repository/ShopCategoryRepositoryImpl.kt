package com.tritongames.shopwishlist.data.repository

import android.app.Application
import android.util.Log
import com.tritongames.shopwishlist.data.models.ShoppingCategoriesDataClass
import com.tritongames.shopwishlist.data.models.ShoppingCategoriesInterface
import com.tritongames.shopwishlist.util.Resource
import retrofit2.Response

class ShopCategoryRepositoryImpl(private val categoryApi: ShoppingCategoriesInterface, val app: Application):
    ShopCategoryRepository {
    override suspend fun getShopCategoryRepo(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCategoriesIcons(token: String): Resource<List<ShoppingCategoriesDataClass>> {
        return try{
            val response = categoryApi.getAllCategoriesIcons()
            val result = response.body()

            if (response.isSuccessful && result != null){

                Resource.Success(result)

            }
            else{
                Resource.Error(response.message())
            }

        }catch(e: Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun getClothesIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getComputersIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiningIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getEbookIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetsIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getTravelIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getGamesIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoCategoryIcon(): Resource<ShoppingCategoriesDataClass> {
        TODO("Not yet implemented")
    }
}