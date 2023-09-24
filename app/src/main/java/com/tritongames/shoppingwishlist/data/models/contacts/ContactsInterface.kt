package com.tritongames.shoppingwishlist.data.models.contacts

import retrofit2.Response
import retrofit2.http.GET

interface ContactsInterface {
    @GET("/ShoppingWishList/")
    suspend fun getContactData(): Response<List<ContactsDataClassItem>>
}