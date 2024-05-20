package com.tritongames.shoppingwishlist.data.viewmodels

import androidx.lifecycle.ViewModel
import com.tritongames.shoppingwishlist.data.repository.userPreferences.UserPreferenceRepository
import com.tritongames.shoppingwishlist.di.PreferenceKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository): ViewModel(){

    suspend fun read(): String?{
        return userPreferenceRepository.getBestBuyKeyValue(PreferenceKey.BEST_BUY_API_KEY)
    }
    suspend fun write(value: String){
        return userPreferenceRepository.saveBestBuyKeyUpdatedValue(PreferenceKey.BEST_BUY_API_KEY, value)
    }
}