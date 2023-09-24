package com.tritongames.shoppingwishlist.data.repository.contacts

import android.app.Application
import android.util.Log
import com.tritongames.shoppingwishlist.data.models.contacts.ContactsDataClassItem
import com.tritongames.shoppingwishlist.data.models.contacts.ContactsInterface
import com.tritongames.shoppingwishlist.util.Resource
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(private val api: ContactsInterface, app: Application):
    ContactsRepository {
    override suspend fun getContactData(): Resource<List<ContactsDataClassItem>> {
       return try {
           val response = api.getContactData()
           val result = response.body()

           Log.d("Contacts Repository Imp", result.toString())
           if (response.isSuccessful && result != null){
               Resource.Success(result)
           }
           else{
               Resource.Error(response.message())
           }
        }catch (e: Exception){
           Resource.Error(e.message ?: "An error occurred")
        }
    }

}