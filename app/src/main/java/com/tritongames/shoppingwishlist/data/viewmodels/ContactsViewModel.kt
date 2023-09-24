package com.tritongames.shoppingwishlist.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.ShoppingData
import com.tritongames.shoppingwishlist.data.repository.contacts.ContactsRepository
import com.tritongames.shoppingwishlist.util.DispatcherProvider
import com.tritongames.shoppingwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactsViewModel @Inject constructor(private val contactsRepo: ContactsRepository, private val dispatchers: DispatcherProvider?): ViewModel(){

    private val passWordList: MutableList<String> = mutableListOf()
    private var emailList: MutableList<String> = mutableListOf()
    private val firstNameList: MutableList<String> = mutableListOf()
    private val lastNameList: MutableList<String> = mutableListOf()
    private val _uiState = MutableStateFlow(ShoppingData())
    private var clientSecretList : MutableList<String> = mutableListOf()
    private var clientIDList: MutableList<Int> = mutableListOf()
    private var ephermalKeySecretList: MutableList<String> = mutableListOf()
  //  private var clientAddressesList: MutableList<Address> = mutableListOf()
    private var clientPhoneNumberList: MutableList<String> = mutableListOf()
    val uiState : StateFlow<ShoppingData> = _uiState.asStateFlow()


    sealed class RecipientLoadingEvent{
        class Success(val resultString: String): RecipientLoadingEvent()
        class Failure(val errorString: String): RecipientLoadingEvent()
        object Empty: RecipientLoadingEvent()
    }

    private val _recipientload = MutableStateFlow<RecipientLoadingEvent>(RecipientLoadingEvent.Empty)
    val recipientLoad: StateFlow<RecipientLoadingEvent> = _recipientload

    fun getPhoneNumber(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getContactData()) {
                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let { clientPhoneNumberList.add(it.phone) }
                    }

                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client secret")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return clientPhoneNumberList

    }

   /* fun getAddress(): MutableList<Address> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getContactData()) {
                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let { clientAddressesList.add(it.address) }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client secret")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return clientAddressesList

    }*/


    fun getClientID(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getContactData()) {
                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let { clientSecretList.add(it.clientId) }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client secret")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return clientSecretList

    }

    fun getEphermalKeySecret(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getContactData()) {
                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let {  ephermalKeySecretList.add(it.ephermalsecret) }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client secret")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return ephermalKeySecretList

    }

    fun getClientSecret(): MutableList<String> {
        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse  = contactsRepo.getContactData()) {
                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        for(i in 0..(loadResponse.data?.size ?: 4)){
                            loadResponse.data?.get(i)?.let { clientSecretList.add(it.clientsecret) }
                        }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load client secret")
                else -> _recipientload.value = RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }
        }
        return clientSecretList

    }

    fun getContactEmails(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getContactData()) {

                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let { emailList.add(it.email) }
                    }
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load full names")
                else -> _recipientload.value =
                    RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }

        }
        return emailList
    }

    fun getContactPasswords(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getContactData()) {
                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let { passWordList.add(it.password) }
                    }
                    Log.d("ContactsViewModel",passWordList.toString())

                }
                is Resource.Error<*> -> ContactsViewModel.RecipientLoadingEvent.Failure("Couldn't load full names")
                else -> _recipientload.value =
                    ContactsViewModel.RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }

        }
        return passWordList
    }

    fun getContactFirstNames(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getContactData()) {

                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let { firstNameList.add(it.firstname) }
                    }
                }
                is Resource.Error<*> -> ContactsViewModel.RecipientLoadingEvent.Failure("Couldn't load full names")
                else -> _recipientload.value =
                    ContactsViewModel.RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }

        }
        return firstNameList
    }

    fun getContactLastNames(): MutableList<String> {

        viewModelScope.launch(dispatchers!!.io) {
            when (val loadResponse = contactsRepo.getContactData()) {

                is Resource.Success<*> ->{
                    for(i in 0..(loadResponse.data?.size ?: 4)){
                        loadResponse.data?.get(i)?.let { lastNameList.add(it.lastname) }
                    }
                }
                is Resource.Error<*> -> ContactsViewModel.RecipientLoadingEvent.Failure("Couldn't load full names")
                else -> _recipientload.value =
                    ContactsViewModel.RecipientLoadingEvent.Failure(loadResponse.errorMsg.toString())
            }

        }
        return lastNameList
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