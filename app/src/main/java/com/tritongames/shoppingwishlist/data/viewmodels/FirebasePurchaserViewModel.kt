package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData
import com.tritongames.shoppingwishlist.data.repository.firebase.DefaultPurchaserDataRepository
import com.tritongames.shoppingwishlist.data.repository.firebase.PurchaserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebasePurchaserViewModel @Inject constructor(
    val fbPurchaserRepo: PurchaserDataRepository,
    val mainDispatcher: MainCoroutineDispatcher,
    val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    companion object {
        val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
        val dispatcherMain: MainCoroutineDispatcher = Dispatchers.Main
        val purchaserRepo: PurchaserDataRepository
            get() {
                return DefaultPurchaserDataRepository(dispatcherMain, dispatcherIO)
            }



        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {


                if (modelClass.isAssignableFrom(FirebasePurchaserViewModel::class.java)) {
                    return FirebasePurchaserViewModel(
                        fbPurchaserRepo = purchaserRepo,
                        mainDispatcher = dispatcherMain,
                        ioDispatcher = dispatcherIO

                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel")
            }


        }
    }

    private val pData =  PurchaserData(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )


    private val _purchaserState = MutableStateFlow(pData)
    val purchaserState: StateFlow<PurchaserData> = _purchaserState

    fun writePurchaserState(newPurchaserState: PurchaserData) {
        _purchaserState.value = newPurchaserState

    }

    fun getPurchaserInfo(purchaserEmail: String){

        viewModelScope.launch(Dispatchers.IO) {

            fbPurchaserRepo.getPurchaserData(purchaserEmail)
        }

    }

    fun savePurchaserInfo(fbPurchaserData: HashMap<String, String>, purchaserEmail: String) {

        viewModelScope.launch(Dispatchers.IO) {
            fbPurchaserRepo.savePurchaserData(fbPurchaserData, purchaserEmail)
        }


    }
}



