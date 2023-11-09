package com.tritongames.shoppingwishlist.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.StackView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.tritongames.shoppingwishlist.BuildConfig
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.context
import com.tritongames.shoppingwishlist.data.viewmodels.BestBuyViewModel
import com.tritongames.shoppingwishlist.data.viewmodels.ShoppingDataViewModel
import com.tritongames.shoppingwishlist.databinding.SpinnerWishListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishList : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var spinnerWishListBinding : SpinnerWishListBinding
    private lateinit var cartAddButton: Button
    lateinit var it: ArrayAdapter<CharSequence>
    var s: Shop? = null
    var rID3 = 0
    var resID1 = 0
    var resID2 = 0
    var resID3 = 0
    var contextWish: Context? = null
    var fName: EditText? = null
    var lName: EditText? = null
    var first: String? = null
    var last: String? = null
    var wish1: String? = null
    var wish2: String? = null
    var wish3: String? = null
    var contactsAdapter: ArrayAdapter<String>? = null
    var wishAdapter: ArrayAdapter<String>? = null
    var contacts: ArrayList<String>? = null
    var mDetector: GestureDetectorCompat? = null
    var name: String? = null
    var itemContacts: String? = null
    lateinit var data: Array<String>
    var sv: StackView? = null
    var s1adapter: ArrayAdapter<CharSequence>? = null
    private lateinit var cat1BadgeText : TextView
    private lateinit var itemSelected : String
    private lateinit var purchaseFCV: FragmentContainerView
    private lateinit var purchasesRV: RecyclerView
    private var lastKnownLocation: Location? = null
    private var likelyPlaceNames: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAddresses: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAttributions: Array<List<*>?> = arrayOfNulls(0)
    private var likelyPlaceLatLngs: Array<LatLng?> = arrayOfNulls(0)
    private lateinit var switchButton : Switch
    private lateinit var popUp : PopupWindow
    private lateinit var layoutParent : ConstraintLayout
    private var currentLocation : Location? = null
    private lateinit var purchaseButton: Button
    // The entry point to the Places API.
    private lateinit var placesClient: PlacesClient


    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val productList : MutableList<String> = mutableListOf()



    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    var locationPermissionGranted = false
    val shoppingVM: ShoppingDataViewModel by viewModels()
    val bestBuyVM: BestBuyViewModel by viewModels()
    val shopDataViewModel: ShoppingDataViewModel by viewModels()



    init {
        contextWish = context
    }

    @SuppressLint("InflateParams")
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spinnerWishListBinding = SpinnerWishListBinding.inflate(layoutInflater)
        setContentView(spinnerWishListBinding.root)
        // Selecting and purchasing items
        contextWish = applicationContext


        productList.add("Levis")
        productList.add("Playstation 5")
        productList.add("Pizza")

        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter<String>(
            this@WishList,
            android.R.layout.simple_spinner_item,
            productList
        )

        // Add to Cart Button
        cartAddButton = spinnerWishListBinding.addToCart
        cartAddButton.setOnClickListener(this)
        cat1BadgeText = spinnerWishListBinding.cartBadge
        purchaseFCV = spinnerWishListBinding.mapFCV



        purchasesRV = spinnerWishListBinding.purchasesRV

        // Google Maps
       Places.initialize(applicationContext, BuildConfig.GOOGLE_MAPS_API_KEY)
        placesClient = Places.createClient(this)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFCV) as SupportMapFragment
        mapFragment.getMapAsync(this)

        layoutParent = findViewById(R.id.layoutParent)


       switchButton = spinnerWishListBinding.autoDetectSwitch



        // Purchase Page
        purchaseButton = spinnerWishListBinding.purchaseButton
        purchaseButton.setOnClickListener(this)



        // collecting drawables from the three ImageViews in com.tritongames.shoppingwishlist.presentation.Shop class and using that to set text to individual EditTexts.
       /* lifecycleScope.launch() {
            shopDataViewModel.shopDataLoad.collect { event ->
                when (event) {
                    is ShoppingDataViewModel.ShopDataLoadEvent.Success -> {
                        val d1: Int? = Shop.dImage1
                        setAdapter(spin1, d1)


                    }

                    is ShoppingDataViewModel.ShopDataLoadEvent.Error -> {

                    }

                    else -> Unit
                }
            }

        }



        spin1?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @Override
            override fun onItemSelected(
                av: AdapterView<*>?, v: View?,
                pos: Int, posID: Long
            ) {
                val selectedItem: String = spin1?.adapter?.getItem(pos).toString()
               // setItem(selectedItem)

            }

            @Override
            override fun onNothingSelected(av: AdapterView<*>?) {
              // setItem(null)
            }
        }*/
    }

   /* private fun setItem(selectedItem: String?) {
        if (selectedItem != null) {
            itemSelected = selectedItem
        }
    }

    private fun getItem() : String {
        return itemSelected
    }
*/

    private fun setAdapter(spinner: Spinner?, d1: Int?) {
        when(d1){
            R.drawable.ic_baseline_shopping ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,
                    shoppingVM.getCategoryList()[0].category
                ) }
            }
            R.drawable.ic_baseline_computers ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[1].category) }
            }
            R.drawable.ic_baseline_dining ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[2].category) }
            }
            R.drawable.ic_baseline_ebook ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[3].category) }
            }
            R.drawable.ic_baseline_games ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[4].category) }
            }
            R.drawable.ic_baseline_movies ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[5].category) }
            }
            R.drawable.ic_baseline_pets ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[6].category) }
            }
            R.drawable.ic_baseline_travel ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[7].category) }
            }
            R.drawable.ic_baseline_no_category ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it,  shoppingVM.getCategoryList()[8].category) }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onMapReady(googleMap: GoogleMap) {
        val gMap: GoogleMap = googleMap

        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Auto Detect Location
                currentLocation = getDeviceCurrentLocation()
                currentLocation?.let { it1 -> LatLng(it1.latitude, it1.longitude) }?.let { it2 ->
                    MarkerOptions()
                        .position(it2)
                        .title("Home")
                }
                moveCamera(gMap, currentLocation)
            }
            else{
                // Manual Detect Location
                val container = SpinnerWishListBinding.inflate(layoutInflater,layoutParent, false) as View
                //layoutInflater2.inflate(R.layout.address_info_layout, null)

                popUp = PopupWindow(container, 400, 400, true)
                popUp.showAtLocation(layoutParent, Gravity.NO_GRAVITY, 500, 500)

            }
        }



        val home = getDeviceLastLocation()

        if (home != null) {
            MarkerOptions()
                .position(LatLng(home.latitude, home.longitude))
                .title("Home")
            moveCamera(gMap, home)
           // markStoreLocationsOnMap(gMap, "Playstation 5")
        }
        else {
            if (switchButton.isChecked) {
                val home2 = getDeviceCurrentLocation()
                if (home2 != null) {
                    MarkerOptions()
                        .position(LatLng(home2.latitude, home2.longitude))
                }
            }
            else {

            }

        }



    }


    private fun markStoreLocationsOnMap(map: GoogleMap, storeProduct: String) {

        getPOIs()?.forEach { it ->
            val categoryNames = it.place.types?.toString()
            when (categoryNames) {
                PlaceTypes.CAFE -> {

                }
                PlaceTypes.RESTAURANT -> {

                }
                PlaceTypes.ELECTRONICS_STORE -> {
                    val locationLatLngList = bestBuyVM.getAllStoreLocationsWithAvailability(storeProduct)
                    locationLatLngList.forEach {
                        map.addMarker(
                            MarkerOptions()
                                .position(it)
                        )
                    }


                }
                PlaceTypes.CLOTHING_STORE -> {

                }

            }

        }
    }
    private fun moveCamera (map : GoogleMap, lastLocation : Location?) {
        if (lastLocation != null) {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                LatLng(lastKnownLocation!!.latitude,
                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
        }
        else {
                Log.d(TAG, "Current location is null. Using defaults.")
             //   Log.e(TAG, "Exception: %s", task.exception)
            map.moveCamera(CameraUpdateFactory
                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                map.uiSettings.isMyLocationButtonEnabled = false
        }
    }

    private fun getPOIs() : MutableList<PlaceLikelihood>? {
        var response: FindCurrentPlaceResponse? = null

    // Use fields to define the data types to return.
            val placeFields: List<Place.Field> = listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.TYPES)

    // Use the builder to create a FindCurrentPlaceRequest.
            val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)

    // Call findCurrentPlace and handle the response (first check that the user has granted permission).
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

                val placeResponse = placesClient.findCurrentPlace(request)
                placeResponse.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        response = task.result
                        for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {
                            Log.i(
                                TAG,
                                "Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}"
                            )
                        }
                    } else {
                        val exception = task.exception
                        if (exception is ApiException) {
                            Log.e(TAG, "Place not found: ${exception.statusCode}")
                        }
                    }
                }
            } else {
                // A local method to request required permissions;
                // See https://developer.android.com/training/permissions/requesting
                getLocationPermission()
            }

        return response?.placeLikelihoods
    }

    private fun getLocationPermission() {

        val permissionGranted = ContextCompat.checkSelfPermission(this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION)

        when (permissionGranted) {
            PackageManager.PERMISSION_GRANTED -> {
                locationPermissionGranted = true
            }
            PackageManager.PERMISSION_DENIED -> {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

            }

        }
     }


    private fun getDeviceLastLocation(): Location? {
        var lastKnownLocation : Location? = null

        try {
                getLocationPermission()
                if (locationPermissionGranted) {
                    val locationResult = fusedLocationProviderClient.lastLocation
                    locationResult.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.result

                        }
                    }
                }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
            return lastKnownLocation
    }

    private fun getDeviceCurrentLocation(): Location? {
        var currentKnownLocation : Location? = null

        try {
            getLocationPermission()
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY,CancellationTokenSource().token)
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        currentKnownLocation = task.result

                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
        return currentKnownLocation
    }

    private fun getAdapter(adapter: ArrayAdapter<CharSequence>, item: String): SpinnerAdapter {
        adapter === ArrayAdapter.createFromResource(
            this,
            getID(item),
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    @SuppressLint("DiscouragedApi")
    private fun getID(item: String): Int {

        var id = 0
        try {
               id = resources.getIdentifier(item, "drawable", "com.tritongames.shoppingwishlist")
        } catch (e: Exception) {
            throw RuntimeException(
                ("No resource ID found for: "
                        + item).toString() + " / " + id, e
            )
        }
        return id
    }

    @JvmName("getName1")
    fun getName(): String {
        lateinit var fName : TextView
        lateinit var lName : TextView
        val name : String?
        val first: String = fName.text.toString()
        val last: String = lName.text.toString()
        name = "$first $last"
        return name
    }

    companion object {

        var wlAdapter: SimpleCursorAdapter? = null
        var str1: String? = null
        var str2: String? = null
        var str3: String? = null
        private val TAG = WishList::class.java.simpleName
        private val DEFAULT_ZOOM = 15
        private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

            // Keys for storing activity state.
            // [START maps_current_place_state_keys]
            private val KEY_CAMERA_POSITION = "camera_position"
            private val KEY_LOCATION = "location"
            // [END maps_current_place_state_keys]

            // Used for selecting the current place.
            private val M_MAX_ENTRIES = 5
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.addToCart -> {
                    val badge1Text = cat1BadgeText.text
                    var badgeValue = badge1Text.toString().toInt()
                    badgeValue += 1
                    cat1BadgeText.text = badgeValue.toString()
                }
                R.id.purchaseButton -> {
                    val purchaseIntent = Intent(this@WishList, CheckoutActivity::class.java)
                    startActivity(purchaseIntent)
                }


            }
        }
    }


}