package com.tritongames.shoppingwishlist.data.repository.contacts

import javax.inject.Inject


class ContactsRepositoryImpl @Inject constructor(): ContactsRepository {
        val TAG: String = "ContactsRepositoryImpl"

    /*override suspend fun getAllContacts(): Resource<List<ContactResponseItem>> {
        return try {
            val response = api.getAllContacts()
            val result = response.body()

            if (response.isSuccessful && result != null) {
               Resource.Success(result)

            }
            else {
                Resource.Error("Response was not successful")
            }

        }catch (e: Exception){
            Resource.Error (e.message ?: "Failed to retrieve contacts")
        }
    }



    override suspend fun addContact(contact: ContactResponseItem): Resource<ContactResponseItem> {

        return try {
            val response = api.addContact(contact)
            var result = response.body()

            Log.d("ContactsRepositoryImpl", response.isSuccessful.toString())
            Log.d("ContactRepositoryImpl", (result != null).toString())
            if (response.isSuccessful && result != null) {
                 Resource.Success(result)
            }
            if (response.isSuccessful && result == null) {
                result = contact
                Resource.Success(result)

            }
            else {
                Resource.Error("Response was not successful")
            }

        }catch (e: Exception){
            Resource.Error (e.message ?: "Failed to retrieve contacts")
        }


    }
*/




}
