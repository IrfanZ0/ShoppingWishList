package com.tritongames.shoppingwishlist.data.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tritongames.shoppingwishlist.data.models.firebase.purchaserdata.PurchaserData
import com.tritongames.shoppingwishlist.data.repository.firebase.Purchaser
import com.tritongames.shoppingwishlist.data.repository.firebase.Recipient
import com.tritongames.shoppingwishlist.util.SignInDetails
import com.tritongames.shoppingwishlist.util.SignUpDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "UserDataViewModel"

@HiltViewModel
class UserDataViewModel @Inject constructor (
    private val pRepo: Purchaser,
    private val rRepo: Recipient
): ViewModel() {

    private val _purchaserDataState = MutableStateFlow<MutableList<PurchaserData>>(mutableListOf(PurchaserData(
        purchaserImage = "",
        firstName = "",
        lastName = "",
        address = "",
        city = "",
        purchaserState = "",
        zipCode = "",
        email = "",
        phone = "",
        location = LatLng(0.0, 0.0)
    )
    )
    )

    val purchaserDataState: StateFlow<MutableList<PurchaserData>> = _purchaserDataState.asStateFlow()

    private val _loginState = MutableStateFlow<SignInDetails>(SignInDetails.Loading)
    val loginState: StateFlow<SignInDetails> = _loginState.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpDetails>(SignUpDetails.Loading)
    val signUpState: StateFlow<SignUpDetails> = _signUpState.asStateFlow()





    private val _updatedEmail = MutableStateFlow<String?>(null)
    val updatedEmail: StateFlow<String?> = _updatedEmail.asStateFlow()

    private val _updatedPassword = MutableStateFlow<String?>(null)
    val updatedPassword: StateFlow<String?> = _updatedPassword.asStateFlow()

    fun updateEmail(email: String){
        _updatedEmail.value = email
    }
    fun updatePassword(password: String){
        _updatedPassword.value = password
    }

    fun getRecipientData(rEmail: String){


        viewModelScope.launch {
            _loginState.value = SignInDetails.Loading

            try{
                val pEmail = updatedEmail.value
                val result = rRepo.getRecipientData(rEmail, pEmail!!)
                _loginState.value = SignInDetails.Success(true )

            }catch(e:Exception){
                _loginState.value = SignInDetails.Error(e.message ?: "Unknown error")
            }

        }
    }

    fun getPurchaserData() {

        if (updatedEmail.value.isNullOrBlank() || updatedPassword.value.isNullOrBlank()) {
            _loginState.value = SignInDetails.Error("Email or password cannot be empty")
            return
        }

        viewModelScope.launch {
            _loginState.value = SignInDetails.Loading
            try {
                val result = pRepo.getPurchaserData(
                    updatedEmail.value.toString(),
                    updatedPassword.value.toString()
                )
                _purchaserDataState.value = result
                _loginState.value = SignInDetails.Success(true)
            }catch(e: Exception){
                _loginState.value = SignInDetails.Error(e.message ?: "Unknown error")

            }
        }
    }

    fun setPurchaserData(purchaserMap: HashMap<String, String>, context: Context) {

        viewModelScope.launch {
            _signUpState.value = SignUpDetails.Loading
            try{
                pRepo.savePurchaserData(purchaserMap, context)
                _signUpState.value = SignUpDetails.Success(true)
            }catch(e: Exception){
                Log.d(TAG, "An error has occurred: ${e.message}")
                _signUpState.value = SignUpDetails.Error("${e.message}")
            }

        }


    }

    fun setRecipientData(purchaserMap: HashMap<String, String>, recipientMap: HashMap<String,String>, context: Context) {
        viewModelScope.launch {
            _signUpState.value = SignUpDetails.Loading
            try{
                rRepo.saveRecipientData(purchaserMap, recipientMap, context)
                _signUpState.value = SignUpDetails.Success(true)
            }catch(e: Exception){
                Log.d(TAG, "An error has occurred: ${e.message}")
                _signUpState.value = SignUpDetails.Error("${e.message}")
            }

        }
    }

    fun onSignUpClick(): Boolean {return true}


}