package com.tritongames.shoppingwishlist.data.repository.userPreferences

import androidx.datastore.preferences.core.Preferences

interface UserPreferenceRepository{
    suspend fun getBestBuyKeyValue(bestBuyApiKey: Preferences.Key<String>): String?
    suspend fun saveBestBuyKeyUpdatedValue(value1: Preferences.Key<String>, value: String)

}