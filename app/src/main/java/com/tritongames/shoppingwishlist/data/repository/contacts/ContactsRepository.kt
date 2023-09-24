package com.tritongames.shoppingwishlist.data.repository.contacts

import com.tritongames.shoppingwishlist.data.models.contacts.ContactsDataClassItem
import com.tritongames.shoppingwishlist.util.Resource

interface ContactsRepository {
        suspend fun getContactData(): Resource<List<ContactsDataClassItem>>
}