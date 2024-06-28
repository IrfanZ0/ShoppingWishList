package com.tritongames.shoppingwishlist.presentation

import com.google.android.gms.maps.model.LatLng
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import com.tritongames.shoppingwishlist.presentation.Shop


class ShopKtTest {

    val addressStringTest = "11149 Valjean Avenue Granada Hills California 91344"
    @Before
    fun setUp() {
    }

    @Test
    fun getCustomLocationFromAddressString() {
        val locationCoordinatesActual: LatLng
        val locationCoordinatedExpected: LatLng = LatLng(34.273500, -118.488060)

        val shopTestClass = Shop()
        locationCoordinatesActual = shopTestClass.

        assertEquals(locationCoordinatedExpected, locationCoordinatesActual)
    }
}