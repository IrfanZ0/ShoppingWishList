package com.tritongames.shoppingwishlist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WishesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
