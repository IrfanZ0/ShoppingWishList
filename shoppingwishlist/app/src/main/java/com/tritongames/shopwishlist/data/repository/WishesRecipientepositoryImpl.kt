package com.tritongames.shopwishlist.data.repository

import android.app.Application
import com.tritongames.shopwishlist.data.WishesApi
import retrofit2.Response

class WishesRecipientRepositoryImpl(private val api: WishesApi, app: Application):
    WishesRecipientRepository {
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var firstWish: String
    lateinit var secondWish: String
    lateinit var thirdWish: String

    override suspend fun getAllWishes(): Response<List<WishesResponse>> {
        return api.getWishes()
    }

    override suspend fun getFullNames(
        fNameList: MutableList<String>?,
        lNameList: MutableList<String>?
    ): Response<MutableList<WishesResponse>> {
        lateinit var fullNameList: Response<MutableList<WishesResponse>>
        if (fNameList != null) {
            for (count in fNameList){

                fullNameList.body().apply { fNameList.toString() + "" + lNameList.toString() }

            }
        }
        return fullNameList
    }

    override suspend fun getFirstName(fName: String): Response<WishesResponse> {
       return api.getFirstName(fName)
    }

    override suspend fun getLastName(lName: String): Response<WishesResponse> {
       return api.getLastName(lName)
    }

    override suspend fun getFirstWish(wish1: String): Response<WishesResponse> {
        return api.getFirstWish(wish1)
    }

    override suspend fun getSecondWish(wish2: String): Response<WishesResponse> {
       return api.getSecondWish(wish2)
    }

    override suspend fun getThirdWish(wish3: String): Response<WishesResponse> {
        return api.getThirdWish(wish3)
    }

   /* override suspend fun setFirstName(@Body fName: String) {
       this.firstName = api.setFirstName(fName).toString()
    }

    override suspend fun setLastName(@Body lName: String) {
        this.lastName = api.setLastName(lName).toString()
    }

    override suspend fun setFirstWish(@Body firstWish: String){
       this.firstWish = api.setFirstWish(firstWish).toString()
    }

    override suspend fun setSecondWish(@Body secondWish: String){
        this.secondWish = api.setSecondWish(secondWish).toString()
    }

    override suspend fun setThirdWish(@Body thirdWish: String) {
       this.thirdWish = api.setThirdWish(thirdWish).toString()
    }*/
}