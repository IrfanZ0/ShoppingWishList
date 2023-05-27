package com.tritongames.shopwishlist.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import com.tritongames.shopwishlist.data.repository.UserPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository): ViewModel(){

   suspend fun read(key: String): String?{
       return userPreferenceRepository.getKeyValue(key)
   }
    suspend fun write(key: String, value: String){
        return userPreferenceRepository.saveKeyValue(key, value)
    }
}