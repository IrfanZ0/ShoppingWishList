package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerCheckoutViewModel @Inject constructor ( private val dispatchers : CoroutineDispatcher) :
    ViewModel() {


  /* fun getPurchaserInfo(pEmail: String) {
        dispatchers?.let {
            viewModelScope.launch(it.io) {
                return@launch fbPurchaserVM.getPurchaserInfo(pEmail)
            }
        }



    }*/

    fun getEphermalKey() {
        dispatchers?.let {
            viewModelScope.launch (it){


            }
        }
    }

    fun getPaymentIntent() {
        dispatchers?.let {
            viewModelScope.launch (it){


            }
        }
    }

    fun getPublishableKey() {
        dispatchers?.let {
            viewModelScope.launch (it){

            }
        }
    }
}