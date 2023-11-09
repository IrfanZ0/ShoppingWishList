package com.tritongames.shoppingwishlist.data.repository.contacts

import com.tritongames.shoppingwishlist.data.models.contacts.ContactResponseItem
import com.tritongames.shoppingwishlist.util.Resource

interface ContactsRepository {
        suspend fun getAllContacts(): Resource<List<ContactResponseItem>>

        suspend fun addContact(contact: ContactResponseItem): Resource<ContactResponseItem>


}