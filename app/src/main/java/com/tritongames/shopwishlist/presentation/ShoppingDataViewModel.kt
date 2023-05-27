package com.tritongames.shopwishlist.presentation

import android.content.ClipData
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shopwishlist.data.ShoppingData
import com.tritongames.shopwishlist.data.ShoppingData.Companion.clip_data_item
import com.tritongames.shopwishlist.data.ShoppingData.Companion.mimeTypes
import com.tritongames.shopwishlist.data.models.ShoppingCategoriesDataClass
import com.tritongames.shopwishlist.data.repository.ShopCategoryRepository
import com.tritongames.shopwishlist.di.PreferenceKey
import com.tritongames.shopwishlist.util.DispatcherProvider
import com.tritongames.shopwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ShoppingDataViewModel @Inject constructor (private val shopCatRepo : ShopCategoryRepository, private val dispatcher: DispatcherProvider, private val dataStore: DataStore<Preferences>) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingData())
    val uiState : StateFlow<ShoppingData> = _uiState.asStateFlow()
    private val TAG : String = ShoppingDataViewModel::class.java.simpleName


    sealed class ShopDataLoadEvent{
        class Success (val resultString: String): ShopDataLoadEvent()
        class Error (val errorString: String): ShopDataLoadEvent()
        object Empty: ShopDataLoadEvent()
    }

    private val _ShopDataLoad = MutableStateFlow<ShopDataLoadEvent>(ShopDataLoadEvent.Empty)
    val shopDataLoad: StateFlow<ShopDataLoadEvent> = _ShopDataLoad


    fun getClipData(categoryTag : String, view : View) : ClipData?
     {
        var categoryClipData : ClipData? = null

        viewModelScope.launch (dispatcher.io){

            var showKey: String? = null
            val userPreferenceKey = dataStore.data
                .catch { exception ->
                    if (exception is IOException){
                        emit(emptyPreferences())
                    }
                    else{
                        throw exception
                    }
                }
                .map{
                        preferences -> showKey = preferences[PreferenceKey.SHOW_KEY] ?: "No Key value"
                }

            when (val dataLoadResponse = showKey?.let { shopCatRepo.getAllCategoriesIcons(it) })
            {
                is Resource.Success ->{
                    _ShopDataLoad.value = ShopDataLoadEvent.Success(dataLoadResponse.data.toString())
                    val categoryText = dataLoadResponse.data!![0].category
                    when (categoryText){
                        "Clothes"-> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[0])

                        }
                        "Computers" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[1])
                        }
                        "Dining" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[2])

                        }
                        "eBooks" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[3])
                        }
                        "Movies" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[2])

                        }
                        "Pets" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[3])

                        }
                        "Travel" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[4])

                        }
                        "Video Games" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[5])

                        }
                        "No Category" -> {
                            categoryClipData = getClipInfo(categoryText, mimeTypes, clip_data_item[6])

                        }
                    }

                }
                is Resource.Error ->{_ShopDataLoad.value = ShopDataLoadEvent.Error(dataLoadResponse.errorMsg.toString())}


                else -> {}
            }

        }


        return categoryClipData

    }

    private fun getClipInfo(categoryText: String, mimeTypes: Array<String>, item: ClipData.Item?): ClipData {
        return ClipData(categoryText, mimeTypes, item)
    }

    fun getCategoryList(): List<ShoppingCategoriesDataClass>{
        var catList: List<ShoppingCategoriesDataClass> = listOf()

        viewModelScope.launch(Dispatchers.IO){
            var showKey: String? = null
            val userPreferenceKey = dataStore.data
                .catch { exception ->
                    if (exception is IOException){
                        emit(emptyPreferences())
                    }
                    else{
                        throw exception
                    }
                }
                .map{
                    preferences -> showKey = preferences[PreferenceKey.SHOW_KEY] ?: "No Key value"
                }
            when(val dataLoadResponse = showKey?.let { shopCatRepo.getAllCategoriesIcons(it) }){
                is Resource.Success ->{
                    catList = dataLoadResponse.data!!
                    _ShopDataLoad.value = ShopDataLoadEvent.Success(catList.toString())
                    Log.d(TAG, catList.toString())

                }
                is Resource.Error ->{
                    _ShopDataLoad.value = ShopDataLoadEvent.Error(dataLoadResponse.errorMsg.toString())

                }
                else -> {}
            }

        }

        return catList
    }


}