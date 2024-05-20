package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData
import com.tritongames.shoppingwishlist.data.repository.firebase.PurchaserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebasePurchaserViewModel @Inject constructor(
    private val fbPurchaserDataRepository: PurchaserDataRepository

): ViewModel() {

    private val dispatchers: CoroutineDispatcher
        get() {
            return Dispatchers.IO
        }

    fun getPurchaserInfo(purchaserEmail: String): List<PurchaserData> {
        val purchaserDataMutableList: MutableList<PurchaserData> = mutableListOf()
        dispatchers.let {
            viewModelScope.launch (it){
                purchaserDataMutableList.addAll(fbPurchaserDataRepository.getPurchaserData(purchaserEmail).toMutableList())
            }
        }
        return purchaserDataMutableList
    }

    fun savePurchaserInfo( fbPurchaserData: HashMap<String, String>, purchaserEmail: String) {
        dispatchers.let {
            viewModelScope.launch (it) {
                fbPurchaserDataRepository.savePurchaserData(fbPurchaserData, purchaserEmail )
            }
        }

    }
}