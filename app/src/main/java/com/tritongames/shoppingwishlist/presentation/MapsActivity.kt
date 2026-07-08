package com.tritongames.shoppingwishlist.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.FirebaseApp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.UserData
import com.tritongames.shoppingwishlist.data.repository.firebase.Purchaser
import com.tritongames.shoppingwishlist.data.repository.firebase.Recipient
import com.tritongames.shoppingwishlist.data.viewmodels.MapsViewModel
import com.tritongames.shoppingwishlist.data.viewmodels.UserDataViewModel
import com.tritongames.shoppingwishlist.ui.theme.ShoppingWishListTheme
import com.tritongames.shoppingwishlist.util.BitmapHelper
import com.tritongames.shoppingwishlist.util.UserDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : ComponentActivity() {
    private val mapsVM: MapsViewModel by viewModels()
    private val userDataVM: UserDataViewModel by viewModels()



    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()



        setContent {
            var isDarkModeOn by remember { mutableStateOf(false) }
            val isDynamicColorOn by remember { mutableStateOf(false) }

            ShoppingWishListTheme(darkTheme = isDarkModeOn, dynamicColor = isDynamicColorOn) {
                val email = intent.getStringExtra("Email") ?: ""
                val firstName = intent.getStringExtra("First Name") ?: ""
                val lastName = intent.getStringExtra("Last Name") ?: ""
                val address = intent.getStringExtra("Address") ?: ""
                val city = intent.getStringExtra("City") ?: ""
                val state = intent.getStringExtra("State") ?: ""
                val zip = intent.getStringExtra("Zip") ?: ""
                val phone = intent.getStringExtra("Phone") ?: ""
                val role = intent.getStringExtra("Role") ?: ""
                val image = intent.getStringExtra("Image") ?: ""

                val location = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("Location", LatLng::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra("Location")
                } ?: LatLng(0.0, 0.0)


                val purchaserData = listOf(
                    UserData(
                        image,
                        firstName,
                        lastName,
                        address,
                        city,
                        state,
                        zip,
                        phone,
                        email,
                        role,
                        location
                    )

                )

                val recipientData = mutableListOf(
                    UserData(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        LatLng(0.0, 0.0)
                    )
                )



                when(userDataVM.loginState.collectAsState().value){
                    is UserDetails.Success -> {
                        recipientData.addAll((userDataVM.loginState.collectAsState().value as UserDetails.Success).userData)
                        userDataVM.getRecipientData(recipientData[0].email)
                    }
                    is UserDetails.Error -> {
                        val errorMessage = (userDataVM.loginState.collectAsState().value as UserDetails.Error).message
                        val context = LocalContext.current
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

                    }
                    is UserDetails.Loading -> {
                        // Place progress bar
                        CircularProgressIndicator()
                    }

                    else -> {}
                }

                isDarkModeOn = mapsVM.setDarkMode.collectAsState().value

                Showscaffold(purchaserData, recipientData, mapsVM, userDataVM, isDarkModeOn)
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun GoogleMapsPreview() {
    ShoppingWishListTheme {
        val  intent = Intent()
        val mapsVM = MapsViewModel()

        val email = intent.getStringExtra("Email") ?: ""
        val firstName = intent.getStringExtra("First Name") ?: ""
        val lastName = intent.getStringExtra("Last Name") ?: ""
        val address = intent.getStringExtra("Address") ?: ""
        val city = intent.getStringExtra("City") ?: ""
        val state = intent.getStringExtra("State") ?: ""
        val zip = intent.getStringExtra("Zip") ?: ""
        val phone = intent.getStringExtra("Phone") ?: ""
        val role = intent.getStringExtra("Role") ?: ""
        val image = intent.getStringExtra("Image") ?: ""
        val location = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Location", LatLng::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("Location")
        } ?: LatLng(0.0, 0.0)


        val purchaserData = listOf(
            UserData(
                image,
                firstName,
                lastName,
                address,
                city,
                state,
                zip,
                phone,
                email,
                role,
                location
            )

        )
        val recipientData = listOf(
            UserData(
                "\"C:\\Users\\iziau\\Github\\app\\src\\main\\res\\drawable\\naveed_syed.xml\"",
                "Naveed",
                "Syed",
                "16364 Index Street",
                "Granada Hills",
                "CA",
                "91344",
                "7472391888",
                "afrozziaulla@gmail.com",
                "recipient",
                LatLng(37.422160, -122.084270)


            ),
            UserData(
                "\"C:\\Users\\iziau\\Github\\app\\src\\main\\res\\drawable\\syed_ziaulla.xml\"",
                "Syed",
                "Ziaulla",
                "923 East San Jose Avenue",
                "Burbank",
                "California",
                "91501",
                "5628957690",
                "syedziaulla@gmail.com",
                "recipient",
                LatLng(34.192730, -118.303642)


            )
        )
        val pRepo = Purchaser()
        val rRepo = Recipient()
        val userDataViewModel = UserDataViewModel(pRepo, rRepo)
        val isDarkModeOn = mapsVM.setDarkMode.collectAsState().value

        Showscaffold(
            purchaserData,
            recipientData,
            mapsVM,
            userDataViewModel,
            isDarkModeOn
        )
    }
}

@Composable
fun LoadRecipients(recipientList: List<UserData>) {
    TODO("Not yet implemented")
}


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Showscaffold(
    purchaserData: List<UserData>,
    recipientData: List<UserData>,
    mapsVM: MapsViewModel,
    userDataVM: UserDataViewModel,
    isDarkModeOn: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome ${purchaserData.firstOrNull()?.firstname ?: ""} to your map") }
            )

        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth().height(200.dp)
            ) {
                CreateRecipientFilter(recipientData, purchaserData, mapsVM, userDataVM)
                SetDarkMode(isDarkModeOn,  mapsVM)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {}
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadGoogleMaps(purchaserData, recipientData, mapsVM)
        }
    }
}

@Composable
fun SetDarkMode(darkModeOn: Boolean, mapsVM: MapsViewModel) {
    var darkOn = darkModeOn

    IconButton(
        onClick = { mapsVM.setDarkModeOn(!darkOn) }
    ){
        Icon(
            painter = if (darkOn) painterResource(R.drawable.darkmode) else painterResource(R.drawable.lightmode),
            contentDescription = if (darkOn) "Dark Mode is On" else "Dark Mode is Off"

        )
    }
}


enum class DistanceFilter {
    NONE, LESS_THAN_5, FIVE_TO_TEN, TEN_OR_MORE
}

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("MutableCollectionMutableState", "DiscouragedApi", "LocalContextResourcesRead")
@Composable
fun CreateRecipientFilter(
    recData: List<UserData>,
    purData: List<UserData>,
    mapsVM: MapsViewModel,
    userDataVM: UserDataViewModel
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf(DistanceFilter.NONE) }

    val recipientDistanceList: MutableList<Float> = remember { mutableListOf() }
    var recDataList: MutableList<UserData> = remember { mutableListOf() }

    // Recipient

    LaunchedEffect(Unit) {
        userDataVM.getRecipientData(recData[0].email)
    }

    val recDataState = userDataVM.loginState.collectAsState()

    when(recDataState.value){
        is UserDetails.Success -> {
            recDataList.clear()
            recDataList.addAll((recDataState.value as UserDetails.Success).userData)

            recDataList.forEach { recData ->
                mapsVM.calculatepurchaserToRecipientDistance(
                    purData[0].location.latitude,
                    purData[0].location.longitude,
                    recData.location.latitude,
                    recData.location.longitude
                )
            }
            recipientDistanceList.clear()
            recipientDistanceList.add(mapsVM.purchaserToRecipientDistance.collectAsState().value ?: 0f)

        }
        is UserDetails.Error -> {
            Log.d("MapsActivity", "No Reciever data found")}

        else -> {}
    }

    Column(
        modifier = Modifier.height(
            height = 400.dp
        ).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start
    ){
        FilterChip(
            selectedFilter == DistanceFilter.LESS_THAN_5,
            onClick = {
                selectedFilter = if (selectedFilter == DistanceFilter.LESS_THAN_5) DistanceFilter.NONE else DistanceFilter.LESS_THAN_5
                val isSelected = selectedFilter == DistanceFilter.LESS_THAN_5

                var show = false
                for (index in 0 until recipientDistanceList.size){
                    if (isSelected && 0 <= recipientDistanceList[index] && recipientDistanceList[index] < 5f){
                        show = true
                        mapsVM.setShowFilteredMarkers(show, index, recDataList)
                        break
                    }
                    else{
                        mapsVM.setShowFilteredMarkers(show, index, recDataList)
                    }
                }

            },
            label = {
                Text(
                    text = "distance < 5",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    softWrap = true,
                    fontSize = 12.sp
                )

            },
            shape = AbsoluteCutCornerShape(15.dp),
            modifier = Modifier.width(150.dp),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = Color.Blue,
                selectedBorderColor = Color.Red,
                borderWidth = 2.dp,
                selectedBorderWidth = 4.dp,
                enabled = true,
                selected = selectedFilter == DistanceFilter.LESS_THAN_5

            ),
            colors = FilterChipDefaults.filterChipColors(
                containerColor = Color.Cyan,
                selectedContainerColor = Color.Magenta,
                selectedLabelColor = Color.White
            )
        )

        FilterChip(
            selectedFilter == DistanceFilter.FIVE_TO_TEN,
            onClick = {
                selectedFilter = if (selectedFilter == DistanceFilter.FIVE_TO_TEN) DistanceFilter.NONE else DistanceFilter.FIVE_TO_TEN
                val isSelected = selectedFilter == DistanceFilter.FIVE_TO_TEN

                var show = false
                for (index in 0 until recipientDistanceList.size){
                    if (isSelected && 5f <= recipientDistanceList[index] && recipientDistanceList[index] < 10f){
                        show = true
                        mapsVM.setShowFilteredMarkers(show, index, recDataList)
                        break
                    } else {
                        mapsVM.setShowFilteredMarkers(show, index, recDataList)
                    }
                }
            },
            label = {
                Text(
                    text ="5 <= distance < 10",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    softWrap = true,
                    fontSize = 12.sp
                )

            },
            shape = androidx.compose.foundation.shape.AbsoluteCutCornerShape(15.dp),
            modifier = Modifier.width(150.dp),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = Color.Blue,
                selectedBorderColor = Color.Red,
                borderWidth = 2.dp,
                selectedBorderWidth = 4.dp,
                enabled = true,
                selected = selectedFilter == DistanceFilter.FIVE_TO_TEN

            ),
            colors = FilterChipDefaults.filterChipColors(
                containerColor = Color.Cyan,
                selectedContainerColor = Color.Magenta,
                selectedLabelColor = Color.White
            )
        )

        FilterChip(
            selectedFilter == DistanceFilter.TEN_OR_MORE,
            onClick = {
                selectedFilter = if (selectedFilter == DistanceFilter.TEN_OR_MORE) DistanceFilter.NONE else DistanceFilter.TEN_OR_MORE
                val isSelected = selectedFilter == DistanceFilter.TEN_OR_MORE

                var show = false
                for (index in 0 until recipientDistanceList.size){
                    if (isSelected && 10f <= recipientDistanceList[index]){
                        show = true
                        mapsVM.setShowFilteredMarkers(show, index, recDataList)
                        break
                    } else {
                        mapsVM.setShowFilteredMarkers(show, index, recDataList)
                    }
                }
            },
            label = {
                Text(
                    text = "distance >= 10",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    softWrap = true,
                    fontSize = 12.sp

                )
            },
            shape = androidx.compose.foundation.shape.AbsoluteCutCornerShape(15.dp),
            modifier = Modifier.width(150.dp),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = Color.Blue,
                selectedBorderColor = Color.Red,
                borderWidth = 2.dp,
                selectedBorderWidth = 4.dp,
                enabled = true,
                selected = selectedFilter == DistanceFilter.TEN_OR_MORE

            ),
            colors = FilterChipDefaults.filterChipColors(
                containerColor = Color.Cyan,
                selectedContainerColor = Color.Magenta,
                selectedLabelColor = Color.White
            )
        )

    }
}


fun addNewRecipient(): () -> Unit {
    return {
        // TODO: Implement logic for adding a new recipient
    }
}

@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
fun LoadGoogleMaps(
    purchaserDataList: List<UserData>,
    recipientDataList: List<UserData>,
    mapsVM: MapsViewModel
) {
    val context = LocalContext.current
    var homeCoordinates by remember { mutableStateOf<LatLng>(purchaserDataList[0].location) }
    val purchaserMarkerExists by mapsVM.purchaserMarkerExists.collectAsState()
    val recipientMarkerExists by mapsVM.recipientMarkerExists.collectAsState()
    val showFilteredMarkers by mapsVM.showFilteredMarkers.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(homeCoordinates, 10f)
    }



    /* val sb = StringBuilder()
     val purchaser = purchaserDataList.firstOrNull()
     val purchaserAddressString = if (purchaser != null) {
         sb.append(purchaser.address)
             .append(", ")
             .append(purchaser.city)
             .append(", ").append(purchaser.state)
             .append(" ").append(purchaser.zip)
             .toString()
     } else {
         ""
     }

     LaunchedEffect(purchaserAddressString) {
         mapsVM.getCoordinates(context, purchaserAddressString) { latLng ->
             homeCoordinates = latLng

         }
     }*/

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onPOIClick = { poi ->
                purchaserDataList.firstOrNull()?.role?.let { role ->
                    when (role) {
                        "Purchaser" -> {
                            if (poi.latLng == homeCoordinates){
                                mapsVM.setPurchaserMarkerExists(true)
                            }
                            else{
                                mapsVM.setPurchaserMarkerExists(false)
                            }

                        }

                        "Recipient" -> {
                            if (poi.latLng == homeCoordinates){
                                mapsVM.setRecipientMarkerExists(true)
                            }
                            else{
                                mapsVM.setRecipientMarkerExists(false)
                            }

                        }
                    }
                }
            }
        ) {
            homeCoordinates.let { coords ->
                val purchaserIconId = context.resources.getIdentifier(
                    purchaserDataList[0].image.substringAfterLast("."),
                    "drawable",
                    context.packageName
                )
                ShowMarker(
                    coords,
                    "Purchaser: ${purchaserDataList[0].firstname}",
                    true,
                    purchaserIconId
                )
            }


            val filteredMarkers by mapsVM.filteredMarkers.collectAsState()
            val showFilteredMarkers by mapsVM.showFilteredMarkers.collectAsState()

            if (showFilteredMarkers) {
                filteredMarkers.forEach { data ->
                    val recIconId = context.resources.getIdentifier(
                        data.image.substringAfterLast("."),
                        "drawable",
                        context.packageName
                    )
                    ShowMarker(
                        data.location,
                        "Recipient: ${data.firstname}",
                        true,
                        recIconId
                    )
                }
            } else {
                recipientDataList.forEach { data ->
                    val recIconId = context.resources.getIdentifier(
                        data.image.substringAfterLast("."),
                        "drawable",
                        context.packageName
                    )
                    ShowMarker(
                        data.location,
                        "Recipient: ${data.firstname}",
                        true,
                        recIconId
                    )
                }
            }

        }

        homeCoordinates?.let { coords ->
            Text("Coordinates: ${coords.latitude}, ${coords.longitude}")
        } ?: Text("Loading location...")
    }
}

@Composable
fun ShowMarker(position: LatLng, title: String, isVisible: Boolean, vectorResId: Int) {
    val context = LocalContext.current
    val icon = if (vectorResId != 0) {
        BitmapHelper().bitmapDescriptorFromVector(context, vectorResId)
    } else {
        null
    }
    Marker(
        state = rememberUpdatedMarkerState(position = position),
        title = title,
        icon = icon,
        visible = isVisible
    )
}
