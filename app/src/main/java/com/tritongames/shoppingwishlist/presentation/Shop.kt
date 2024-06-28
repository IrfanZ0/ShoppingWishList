package com.tritongames.shoppingwishlist.presentation

import android.graphics.Bitmap
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.context
import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData
import com.tritongames.shoppingwishlist.data.viewmodels.FirebasePurchaserViewModel
import com.tritongames.shoppingwishlist.presentation.ui.theme.ShoppingWishListTheme
import kotlinx.coroutines.launch
import java.util.Locale


class Shop: ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fbPurchaserVM: FirebasePurchaserViewModel by viewModels {FirebasePurchaserViewModel.Factory}
        lateinit var purchaserData: PurchaserData
        val email = Firebase.auth.currentUser?.email
        val purchaserSB = StringBuilder()
        lifecycleScope.launch {
            fbPurchaserVM.purchaserState.collect {pState ->
                purchaserSB.append(pState.address)
                .append(pState.city)
                .append(pState.purchaserState)
                .append(pState.zipCode)
            }
        }

        setContent {
            ShoppingWishListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (email != null) {

                        ShoppingScene(purchaserSB.toString(), fbPurchaserVM)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getCustomLocation(addressStringText: String): LatLng {
        var latLng: LatLng = LatLng(34.273500, -118.488060)



// Create a Geocoder instance
        val maxResults = 1
// Check if the SDK version supports the new GeocodeListener
        if (Build.VERSION.SDK_INT >= 33) {

            context?.let { Geocoder(it, Locale.getDefault()) }
                ?.getFromLocationName("11149 Valjean Avenue, Granada Hills, California, 91344", maxResults, Geocoder.GeocodeListener {
                        latLng = LatLng(it[0].latitude, it[0].longitude)
                        Log.d("Shop", "The locations found are: $latLng")
                })
        }
        return latLng
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ShoppingScene(
    userAddress: String,
    fbPurchaserVM: FirebasePurchaserViewModel = viewModel(factory = FirebasePurchaserViewModel.Factory)
) {
    var itemSearched by remember { mutableStateOf("")}

    // Item Search section
    Column(
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to the Shopping Scene!",
        )
        TextField(
            value = itemSearched,
            onValueChange = {itemSearched = it},
            label = {Text("Type an item to search on map")}
        )

//        val sbAddress = StringBuilder()
//        sbAddress
//            .append(userAddress[0])
//            .append(userAddress[1])
//            .append(userAddress[2])
//            .append(userAddress[3])
//        val address = Address(Locale.US)
//        address.setAddressLine(0, sbAddress.toString())
//        val addressList = mutableListOf(address.getAddressLine(0))
        val bitmapConfig = Bitmap.Config.ARGB_8888
        val bitmap_home = Bitmap.createBitmap(48,48, bitmapConfig)
        Log.d("Shop", "Home Address Location: getCustomLocation(address = \"11149 Valjean Avenue, Granada Hills, California, 91344\")")
        val homeLocation = getCustomLocation(address = "11149 Valjean Avenue, Granada Hills, California, 91344")
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                homeLocationn,
                20f
            )
        }
        GoogleMap(modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker (
                state = MarkerState(position = getCustomLocation(address = "11149 Valjean Avenue, Granada Hills, California, 91344")),
                title = "Home",
                icon = BitmapDescriptorFactory.fromBitmap(bitmap_home)

            )

        }

    }


}
//
//@Composable
//fun ShoppingScenePrep (fbVM: FirebasePurchaserViewModel, fbAuthImpl: FirebaseAuthImpl) {
//
//    if (fbAuthImpl.hasUser()) {
//        val userEmail = fbAuthImpl.currentUserEmail
//        ShopScene(
//            email = userEmail,
//            address = fbVM.getPurchaserInfo(userEmail)
//        )
//
//    }
//
//
//}
//
//@Composable
//fun ShopScene(email: String, address: List<PurchaserData>) {
//
//}
/*
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun ShoppingScenePreview(

    purchaserList: List<PurchaserData> = listOf(
        PurchaserData(
            "",
            "Irfan",
            "Ziaulla",
            "11149 Valjean Avenue",
            "Granada Hills",
            "California",
            "91344",
            "iziaulla@gmail.com",
            "8185312618",
            "IrfanZ",
            "SanDiego"
        )
    )
){

    ShoppingWishListTheme {
        ShoppingScene(purchaserList[0].email, purchaserList)
    }

}*/




@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun getCustomLocation(address: String): LatLng {
    val context = LocalContext.current

// Create a Geocoder instance
    val geocoder = Geocoder(context, Locale.getDefault())
    val maxResults = 1
    var coordinates: LatLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
// Check if the SDK version supports the new GeocodeListener
    if (Build.VERSION.SDK_INT >= 33) {
        Log.d("Shop", address.toString())
        geocoder.getFromLocationName(address, maxResults) {
             coordinates = LatLng(it[0].latitude,it[0].longitude)

            Log.d("Shop", coordinates.toString())

        }

    }

    return coordinates
}