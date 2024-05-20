package com.tritongames.shoppingwishlist.presentation

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.models.firebase.FirebaseAuthImpl
import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData
import com.tritongames.shoppingwishlist.data.repository.firebase.PurchaserDataRepository
import com.tritongames.shoppingwishlist.data.viewmodels.FirebasePurchaserViewModel
import com.tritongames.shoppingwishlist.presentation.ui.theme.ShoppingWishListTheme
import javax.inject.Inject

class Shop @Inject constructor(private val purchaserDataRepository: PurchaserDataRepository) : ComponentActivity() {
    val pDataRepo = purchaserDataRepository
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingWishListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val fbVM = FirebasePurchaserViewModel(purchaserDataRepository)
                    val fbAuthImpl = FirebaseAuthImpl()
                    val email = fbAuthImpl.currentUserEmail
                    val address = fbVM.getPurchaserInfo(email)

                    ShoppingScene(email, address)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ShoppingScene(userEmail: String, userHomeAddress: List<PurchaserData>) {
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
    }
    // Map Section
    Column(
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val sbAddress = StringBuilder()
        sbAddress
            .append(userHomeAddress[0].address)
            .append(userHomeAddress[0].city)
            .append(userHomeAddress[0].purchaserState)
            .append(userHomeAddress[0].zipCode)

        val addressList = listOf(sbAddress.toString())

        val homeAddressLatLng = getCustomLocation(addressList)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(homeAddressLatLng, 10f)
        }
        GoogleMap(modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = homeAddressLatLng),
                title = "Home",
                snippet = "Marker in Home",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.home)
            )
        }




    }

}

@Composable
fun ShoppingScenePrep (fbVM: FirebasePurchaserViewModel, fbAuthImpl: FirebaseAuthImpl) {

    if (fbAuthImpl.hasUser()) {
        val userEmail = fbAuthImpl.currentUserEmail
        ShopScene(
            email = userEmail,
            address = fbVM.getPurchaserInfo(userEmail)
        )

    }


}

@Composable
fun ShopScene(email: String, address: List<PurchaserData>) {

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun ShoppingScenePreview(
    userEmail: String = "iziaulla@gmail.com",
    address: List<PurchaserData> = listOf(
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
        ShoppingScene(userEmail, address)
    }

}




@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun getCustomLocation(addressList: List<String>): LatLng {
    lateinit var latLng: LatLng
    val context = LocalContext.current
    val addressString: StringBuilder = StringBuilder()
    for(address in addressList) {
        addressString.append(address)
    }
    val gc = Geocoder(context)

    val locationList = gc.getFromLocationName(
        addressString.toString(),
        1,
        -90.0,
        -180.0,
        90.0,
        180.0
    )
    val customLocationLatitude = locationList?.get(0)?.latitude
    val customLocationLongitude = locationList?.get(0)?.longitude

    latLng = LatLng(customLocationLatitude!!, customLocationLongitude!!)

    return latLng
}