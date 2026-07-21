package com.tritongames.shoppingwishlist.data.repository.userPreferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.context
import com.tritongames.shoppingwishlist.di.AppModule.datastore
import com.tritongames.shoppingwishlist.di.PreferenceKey
import javax.inject.Inject
import kotlin.text.get

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
  ): UserPreferenceRepository {

    override suspend fun getBestBuyKeyValue(bestBuyApiKey: Preferences.Key<String>): String {
        val bbKey = dataStore.data.map{
           userPreferences ->
            userPreferences[PreferenceKey.BEST_BUY_API_KEY]
        }
        return bbKey.toString()
    }

    override suspend fun saveBestBuyKeyUpdatedValue(value1: Preferences.Key<String>, value: String) {
         context?.datastore?.edit {
                    userPreferences -> userPreferences[PreferenceKey.BEST_BUY_API_KEY]
                userPreferences[PreferenceKey.BEST_BUY_API_KEY] = value

         }
    }


}
