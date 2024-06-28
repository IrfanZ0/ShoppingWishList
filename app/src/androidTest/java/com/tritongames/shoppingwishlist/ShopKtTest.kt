package com.tritongames.shoppingwishlist

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.android.gms.maps.model.LatLng
import com.tritongames.shoppingwishlist.presentation.Shop
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class ShopKtTest {


    @get:Rule
    val composeShopTestRule = createAndroidComposeRule<Shop>()

    @Test
    fun getCustomLocationFromAddressString() {
        val addressStringText = "11149 Valjean Avenue, Granada Hills, California, 91344"
        var locationCoordinatesActual: LatLng = composeShopTestRule.activity.getCustomLocation(addressStringText)
        val locationCoordinatedExpected: LatLng = LatLng(34.273500, -118.488060)
        val bitmapConfig = Bitmap.Config.ARGB_8888
        val bitmap_home = Bitmap.createBitmap(48,48, bitmapConfig)

       /* composeShopTestRule.setContent {

            GoogleMap(modifier = Modifier.fillMaxSize(),
                cameraPositionState = rememberCameraPositionState()
            ) {
                Marker (
                    state = MarkerState(position = locationCoordinatesActual),
                    title = "Home",
                    icon = BitmapDescriptorFactory.fromBitmap(bitmap_home)

                )

            }
        }*/

        Log.d("ShopKtTest", locationCoordinatesActual.toString())
        assertEquals(locationCoordinatedExpected, locationCoordinatesActual)
    }
}