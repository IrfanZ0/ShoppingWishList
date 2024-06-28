package com.tritongames.shoppingwishlist.data.models.contacts

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContactApi {
    @GET("/contacts/Contacts.json")
    suspend fun getAllContacts() : Response<List<ContactResponseItem>>
    @POST("/contacts/new")
    suspend fun addContact(@Body contact:ContactResponseItem): Response<ContactResponseItem>
}