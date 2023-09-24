package com.tritongames.shoppingwishlist.data.repository.category

import android.app.Application
import com.tritongames.shoppingwishlist.data.models.categories.ShoppingCategoriesDataClass
import com.tritongames.shoppingwishlist.data.models.categories.ShoppingCategoriesInterface
import com.tritongames.shoppingwishlist.util.Resource
import javax.inject.Inject

class ShopCategoryRepositoryImpl @Inject constructor(private val categoryApi: ShoppingCategoriesInterface, val app: Application):
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