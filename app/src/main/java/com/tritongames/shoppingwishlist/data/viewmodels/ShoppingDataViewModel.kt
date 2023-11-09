package com.tritongames.shoppingwishlist.data.viewmodels

import android.content.ClipData
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.ShoppingData
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.clip_data_item
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.mimeTypes
import com.tritongames.shoppingwishlist.data.models.categories.ShoppingCategoriesDataClass
import com.tritongames.shoppingwishlist.data.repository.category.ShopCategoryRepository
import com.tritongames.shoppingwishlist.di.PreferenceKey
import com.tritongames.shoppingwishlist.util.DispatcherProvider
import com.tritongames.shoppingwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ShoppingDataViewModel @Inject constructor (private val shopCatRepo: ShopCategoryRepository?, private val dispatcher: DispatcherProvider?, private val dataStore: DataStore<Preferences>?) : ViewModel() {
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

        dispatcher?.let {
            viewModelScope.launch (it.io){

                var showKey: String? = null
                val userPreferenceKey = dataStore?.data
                    ?.catch { exception ->
                        if (exception is IOException){
                            emit(emptyPreferences())
                        } else{
                            throw exception
                        }
                    }
                    ?.map{ preferences -> showKey = preferences[PreferenceKey.SHOW_KEY] ?: "No Key value"
                    }

                when (val dataLoadResponse = showKey?.let { shopCatRepo?.getAllCategoriesIcons(it)
                     }) {
                    is Resource.Success<*> ->{
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
            val userPreferenceKey = dataStore?.data
                ?.catch { exception ->
                    if (exception is IOException){
                        emit(emptyPreferences())
                    } else{
                        throw exception
                    }
                }
                ?.map{ preferences -> showKey = preferences[PreferenceKey.SHOW_KEY] ?: "No Key value"
                }
            when(val dataLoadResponse = showKey?.let { shopCatRepo?.getAllCategoriesIcons(it)   }){
                is Resource.Success ->{
                    catList = (dataLoadResponse.data as List<ShoppingCategoriesDataClass>?)!!
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

    fun getProductImages(): MutableList<String>{
        val modelImagesStrings : MutableList<String> =
        mutableListOf()
        modelImagesStrings.add("sampledata/Laptop/Pics/Laptop0120.jpg")
        return modelImagesStrings
    }

    fun getProductCheckoutList(product: String?) : MutableList<String> {
        val productList : MutableList<String> = mutableListOf()
        val productName = product?.substringBefore('$')
        val productPrice = product?.substringAfter('$')
        if (productName != null) {
            productList.add(productName)
        }
        if (productPrice != null) {
            productList.add(productPrice)
        }

        return productList

    }


}