package com.tritongames.shopwishlist.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tritongames.shopwishlist.di.PreferenceKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>): UserPreferenceRepository{

    override suspend fun getKeyValue(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]

    }

    override suspend fun saveKeyValue(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { keyValue -> keyValue[dataStoreKey] = value }
    }
}