package com.tritongames.shoppingwishlist.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.contacts.ContactResponseItem
import com.tritongames.shoppingwishlist.data.repository.contacts.ContactsRepository
import com.tritongames.shoppingwishlist.util.DispatcherProvider
import com.tritongames.shoppingwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactsViewModel @Inject constructor(private val contactsRepo: ContactsRepository, private val dispatchers: DispatcherProvider?): ViewModel(){

    private val passWordList: MutableList<String> = mutableListOf()
    private var emailList: MutableList<String> = mutableListOf()
    private val firstNameList: MutableList<String> = mutableListOf()
    private val lastNameList: MutableList<String> = mutableListOf()
    private var userNameList : MutableList<String> = mutableListOf()
    private var phoneList: MutableList<String> = mutableListOf()
    private var idList: MutableList<Int> = mutableListOf()
    private var addressList: MutableList<String> = mutableListOf()
    private var cityList: MutableList<String> = mutableListOf()
    private var stateList: MutableList<String> = mutableListOf()
    private var zipCodeList: MutableList<String> = mutableListOf()


    sealed class RecipientLoadingEvent{
        class Success(val resultString: String): RecipientLoadingEvent()
        class Failure(val errorString: String): RecipientLoadingEvent()
        object Empty: RecipientLoadingEvent()
    }

    private val _recipientload = MutableStateFlow<RecipientLoadingEvent>(RecipientLoadingEvent.Empty)
    val recipientLoad: StateFlow<RecipientLoadingEvent> = _recipientload

    fun getPhoneNumber(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getAllContacts()) {
                is Resource.Success<*> ->{
                    for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!){
                        val dataLoad = loadResponse.data[2].dataList[i]
                        dataLoad?.let { phoneList.add(it.phone) }
                    }

                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client's phone number")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return phoneList

    }

    fun getAddress(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getAllContacts()) {
                is Resource.Success<*> ->{
                    for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!){
                        val dataLoad = loadResponse.data[2].dataList[i]
                        dataLoad?.let { addressList.add(it.address) }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client's address")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return addressList

    }


    fun getID(): MutableList<Int> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getAllContacts()) {
                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.get(2)?.dataList?.size!!)){
                        val dataLoad = loadResponse.data[2].dataList[i]
                        dataLoad?.let { idList.add(it.id.toInt()) }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client's ID")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return idList

    }

    fun getUserName(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getAllContacts()) {
                is Resource.Success<*> ->{
                    for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!){
                        val dataLoad = loadResponse.data[2].dataList[i]
                        dataLoad?.let { userNameList.add(it.username) }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client's user name")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return userNameList

    }

    fun getCity(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getAllContacts()) {
                is Resource.Success<*> ->{

                        for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!) {
                            val dataLoad = loadResponse.data[2].dataList[i]
                            dataLoad?.let { cityList.add(it.city) }
                        }

                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client's city")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return cityList

    }

    fun getState(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getAllContacts()) {
                is Resource.Success<*> ->{

                        for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!){
                            val dataLoad = loadResponse.data[2].dataList[i]
                            dataLoad?.let { stateList.add(it.state) }
                        }

                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client's state")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return stateList

    }

    fun getContactEmails(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getAllContacts()) {

                is Resource.Success ->{

                    for(i in 0..<(loadResponse.data?.count()?.minus(1))!!){
                        if (loadResponse.data[i].name == "data") {
                            val dataLoad = loadResponse.data[i].dataList[0]
                            dataLoad?.let { emailList.add(it.email) }
                        }

                    }


                  _recipientload.value = RecipientLoadingEvent.Success("The first email found: ${emailList[0]}")

                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client's emails")
                else -> _recipientload.value =
                    RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }

        }
        return emailList
    }

    fun getContactPasswords(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getAllContacts()) {
                is Resource.Success<*> ->{

                    for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!){
                        val dataLoad = loadResponse.data[2].dataList[i]
                        dataLoad?.let { passWordList.add(it.password) }
                    }
                    Log.d("ContactsViewModel",passWordList.toString())

                }
                is Resource.Error<*> -> ContactsViewModel.RecipientLoadingEvent.Failure("Couldn't load client's password")
                else -> _recipientload.value =
                    ContactsViewModel.RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }

        }
        return passWordList
    }

    fun getFirstNames(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getAllContacts()) {

                is Resource.Success<*> ->{
                    for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!){
                        val dataLoad = loadResponse.data[2].dataList[i]
                        dataLoad?.let { firstNameList.add(it.firstname) }
                    }
                }
                is Resource.Error<*> -> ContactsViewModel.RecipientLoadingEvent.Failure("Couldn't load client's first name")
                else -> _recipientload.value =
                    ContactsViewModel.RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }

        }
        return firstNameList
    }

    fun getLastNames(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getAllContacts()) {

                is Resource.Success<*> ->{
                    for(i in 0..loadResponse.data?.get(2)?.dataList?.size!!){
                        val dataLoad = loadResponse.data[2].dataList[i]
                        dataLoad?.let { lastNameList.add(it.lastname) }
                    }
                }
                is Resource.Error<*> -> ContactsViewModel.RecipientLoadingEvent.Failure("Couldn't load client's last name")
                else -> {}
            }

        }
        return lastNameList
    }

    fun addContact(contact: ContactResponseItem) {

        viewModelScope.launch(dispatchers!!.io) {
            when (val postResponse = contactsRepo.addContact(contact)) {
                is Resource.Success<*> -> {
                    _recipientload.value = RecipientLoadingEvent.Success ("${contact.dataList[0]?.firstname} has been successfully been added")
                    Log.d("ContactsViewModel",postResponse.data.toString())

                }
                is Resource.Error<*> -> {
                    _recipientload.value = RecipientLoadingEvent.Failure("Couldn't add new contact")
                }

                else -> {}
            }
        }

    }



    fun checkUserFirstName(firstName: String, firstNameList: MutableList<String>): Boolean{
        var firstNameMatch = false
        for (fName in firstNameList){
            firstNameMatch = fName.contentEquals(firstName)
        }
        return firstNameMatch
    }

    fun checkUserLastName(lastName: String, lastNameList: MutableList<String>): Boolean{
        var lastNameMatch = false
        for (lName in lastNameList){
            lastNameMatch = lName.contentEquals(lastName)
        }
        return lastNameMatch
    }

}