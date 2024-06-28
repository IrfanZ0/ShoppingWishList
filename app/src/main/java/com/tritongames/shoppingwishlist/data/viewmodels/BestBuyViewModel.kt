package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tritongames.shoppingwishlist.data.models.bestbuy.Image
import com.tritongames.shoppingwishlist.data.repository.bestbuy.BestBuyRepository
import com.tritongames.shoppingwishlist.data.repository.userPreferences.UserPreferenceRepository
import com.tritongames.shoppingwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestBuyViewModel @Inject constructor(private val bbRepository: BestBuyRepository, private val userPreferenceRepository: UserPreferenceRepository, val dispatchers: CoroutineDispatcher): ViewModel() {

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

    fun getAllGamingImages() : List<String>{
        var gamingProductImageList: List<String> = listOf()

        dispatchers.let { it ->
            viewModelScope.launch(it) {
                when (val loadResponse =
                    dataStoreVM.read()?.let { bbRepository.getBestBuyGamingProducts(it) }){
                    is Resource.Success -> {gamingProductImageList =
                        listOf(loadResponse.data?.get(0)?.products?.get(0)?.images.toString())
                    }
                    is Resource.Error -> _productload.value = BestBuyLoadingEvent.Error{loadResponse.errorMsg.toString()}
                    else -> {}
                }

            }
        }
        return gamingProductImageList
    }

    fun getAllStoreLocationsWithAvailability(productName : String) : List<LatLng>{
        var gamingStoreLocationList: List<LatLng> = listOf()

        dispatchers.let { it ->
            viewModelScope.launch(it) {
                when (val loadResponse =
                    dataStoreVM.read()?.let { bbRepository.getStores(it) }){
                    is Resource.Success -> {
                        for (i in 0 ..(loadResponse.data?.size ?: 0)) {
                            if (loadResponse.data?.get(i)?.product?.get(i)?.name == productName) {
                                gamingStoreLocationList =
                                    listOf(
                                        LatLng(
                                            loadResponse.data[i].stores[i].lat, loadResponse.data[i].stores[i].lng
                                        )
                                    )

                            }

                        }

                    }
                    is Resource.Error -> _productload.value = BestBuyLoadingEvent.Error{loadResponse.errorMsg.toString()}
                    else -> {}
                }

            }
        }
        return gamingStoreLocationList
    }




}



