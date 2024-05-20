package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.repository.firebase.PurchaserDataRepository
import com.tritongames.shoppingwishlist.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerCheckoutViewModel @Inject constructor (private val purchaserDataRepository: PurchaserDataRepository, private val dispatchers : DispatcherProvider?) : ViewModel() {

    private val fbPurchaserVM: FirebasePurchaserViewModel = FirebasePurchaserViewModel(purchaserDataRepository)

    fun getPurchaser(pEmail: String) {
        dispatchers?.let {
            viewModelScope.launch (it.io){
              fbPurchaserVM.getPurchaserInfo(pEmail)
            }
        }
    }

    fun getEphermalKey() {
        dispatchers?.let {
            viewModelScope.launch (it.io){


            }
        }
    }

    fun getPaymentIntent() {
        dispatchers?.let {
            viewModelScope.launch (it.io){


            }
        }
    }

    fun getPublishableKey() {
        dispatchers?.let {
            viewModelScope.launch (it.io){

            }
        }
    }
}