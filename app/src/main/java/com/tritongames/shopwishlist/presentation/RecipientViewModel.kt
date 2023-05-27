package com.tritongames.shopwishlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shopwishlist.data.ShoppingData
import com.tritongames.shopwishlist.data.repository.WishesRecipientRepository
import com.tritongames.shopwishlist.util.DispatcherProvider
import com.tritongames.shopwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipientViewModel @Inject constructor(private val recRepo: WishesRecipientRepository, private val dispatchers: DispatcherProvider): ViewModel(){
    private val _uiState = MutableStateFlow(ShoppingData())
    val uiState : StateFlow<ShoppingData> = _uiState.asStateFlow()


    sealed class RecipientLoadingEvent{
        class Success(val resultString: String): RecipientLoadingEvent()
        class Failure(val errorString: String): RecipientLoadingEvent()
        object Empty: RecipientLoadingEvent()
    }

    private val _recipientload = MutableStateFlow<RecipientLoadingEvent>(RecipientLoadingEvent.Empty)
    val recipientLoad: StateFlow<RecipientLoadingEvent> = _recipientload

    fun loadRecipientNames(): MutableList<String> {
        var recipientList: MutableList<String> = mutableListOf()
        viewModelScope.launch(dispatchers.io) {
            when (val loadResponse = recRepo.getFullNames(null, null)) {
                is Resource.Success<*> ->{recipientList = listOf(loadResponse.data!!) as MutableList<String>
                }
                is Resource.Error<*> -> RecipientLoadingEvent.Failure("Couldn't load full names")
                else -> _recipientload.value =
                    RecipientLoadingEvent.Failure(loadResponse.message().toString())
            }

        }
        return recipientList
    }

}