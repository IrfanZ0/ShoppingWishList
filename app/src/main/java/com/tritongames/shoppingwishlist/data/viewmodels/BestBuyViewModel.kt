package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.bestbuy.Image
import com.tritongames.shoppingwishlist.data.repository.bestbuy.BestBuyRepository
import com.tritongames.shoppingwishlist.data.repository.userPreferences.UserPreferenceRepository
import com.tritongames.shoppingwishlist.util.DispatcherProvider
import com.tritongames.shoppingwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestBuyViewModel @Inject constructor(private val bbRepository: BestBuyRepository, private val userPreferenceRepository: UserPreferenceRepository, val dispatchers: DispatcherProvider?): ViewModel() {

    fun getBestBuyRepository(): BestBuyRepository{
        return bbRepository
    }

    fun getUserPrefRepository(): UserPreferenceRepository{
        return userPreferenceRepository
    }

    fun getDispatcherProvider(): DispatcherProvider?{
        return dispatchers
    }

    private var dataStoreVM: DataStoreViewModel = DataStoreViewModel(userPreferenceRepository)
    private val _uiState = MutableStateFlow(BestBuyLoadingEvent.Empty)
    val uiState : StateFlow<BestBuyLoadingEvent.Empty> = _uiState.asStateFlow()


    sealed class BestBuyLoadingEvent{
        class Success(val imageList: List<Image>): BestBuyLoadingEvent()
        class Error(val errorString: () -> String): BestBuyLoadingEvent()
        object Empty: BestBuyLoadingEvent()
    }

    private val _productload = MutableStateFlow <BestBuyLoadingEvent>(uiState.value)
    val productLoad: MutableStateFlow<BestBuyLoadingEvent> = _productload



    fun getAllPS4Images() : List<String>{
        var ps4ProductImageList: List<String> = listOf()

        dispatchers?.let { it ->
            viewModelScope.launch(it.io) {
                when (val loadResponse =
                    dataStoreVM.read()?.let { bbRepository.getBestBuyPS4Products(it) }){
                    is Resource.Success -> {ps4ProductImageList =
                        listOf(loadResponse.data?.get(0)?.products?.get(0)?.images.toString())!!
                    }
                    is Resource.Error -> _productload.value = BestBuyLoadingEvent.Error{loadResponse.errorMsg.toString()}
                    else -> {}
                }

            }
        }
        return ps4ProductImageList
    }




}



