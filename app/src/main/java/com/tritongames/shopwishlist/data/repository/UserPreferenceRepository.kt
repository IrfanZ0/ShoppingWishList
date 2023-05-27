package com.tritongames.shopwishlist.data.repository

import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


interface UserPreferenceRepository{
   suspend fun getKeyValue(key: String): String?
   suspend fun saveKeyValue(key: String, value: String)
}