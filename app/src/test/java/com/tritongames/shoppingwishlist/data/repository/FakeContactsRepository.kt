package com.tritongames.shoppingwishlist.data.repository

import com.tritongames.shoppingwishlist.data.models.Wishes
import com.tritongames.shoppingwishlist.data.models.contacts.Address
import com.tritongames.shoppingwishlist.data.models.contacts.ContactsDataClassItem
import com.tritongames.shoppingwishlist.data.repository.contacts.ContactsRepository
import com.tritongames.shoppingwishlist.util.Resource

class FakeContactsRepository: ContactsRepository {
    private var shouldReturnNetworkError = false

    private val fakeAddress : List<Address> = listOf(Address("1313 Mocking Bird Lane", "Translyvania", "Pennsylvania", "90055"))
    private val clientID = "100"
    private val clientSecret = "abc123"
    private val clientEmail = "xman@gmail.com"
    private val password = "xyz456"
    private val ephermalKey = "Mario"
    private val ephermalSecret = "Luigi"
    private val firstName = "Irfan"
    private val lastName = "Ziaulla"
    private val wishes: List<Wishes> = listOf(Wishes("Playstation 5", "Tesla", "Money"))


    fun setReturnNetworkError (value: Boolean){
        shouldReturnNetworkError = value
    }

    val contactInfo = mutableListOf<ContactsDataClassItem>()

    override suspend fun getContactData(): Resource<ContactsDataClassItem> {
        return if(shouldReturnNetworkError){
            Resource.Error("Error")

        }else{
            Resource.Success(ContactsDataClassItem(1, fakeAddress, clientID, clientSecret, clientEmail, password,ephermalKey,ephermalSecret, firstName, lastName, wishes ))
        }
    }
}