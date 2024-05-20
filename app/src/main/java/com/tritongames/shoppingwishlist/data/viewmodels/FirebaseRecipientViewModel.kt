package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.firebase.RecipientData
import com.tritongames.shoppingwishlist.data.repository.firebase.RecipientDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseRecipientViewModel @Inject constructor(
    private val recipientDataRepository: RecipientDataRepository

): ViewModel() {

    private val dispatchers: CoroutineDispatcher
        get() {
            return Dispatchers.IO
        }

    fun getAllRecipientInfo(purchaserEmail: String): List<RecipientData> {
        lateinit var recipientList: MutableList<RecipientData>
        dispatchers.let {
            viewModelScope.launch(it) {
                recipientList.addAll(recipientDataRepository.getAllRecipientData(purchaserEmail))

            }
        }
        return recipientList.toList()
    }

    fun saveRecipientInfo(rDataMap: HashMap<String,String>, pEmail: String, rEmail: String) {
        dispatchers.let {
            viewModelScope.launch (it){
                recipientDataRepository.saveRecipientData(rDataMap, pEmail, rEmail)
            }
        }
    }


}