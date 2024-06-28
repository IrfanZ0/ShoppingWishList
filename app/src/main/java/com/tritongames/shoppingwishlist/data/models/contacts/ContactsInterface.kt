package com.tritongames.shoppingwishlist.data.models.contacts

import retrofit2.Response
import retrofit2.http.GET

interface ContactsInterface {
    @GET("/IrfanZ0/ShoppingWishList/blob/main/shoppingwishlist/app/src/main/java/com/tritongames/shopwishlist/docs/contacts")
    suspend fun getContactData(): Response<ContactsDataClassItem>
}