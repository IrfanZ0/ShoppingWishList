package com.tritongames.shoppingwishlist.data.viewmodels

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.tritongames.shoppingwishlist.data.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException

class MapsViewModel: ViewModel() {
    private val _purchaserLocation = MutableStateFlow<LatLng>(LatLng(0.0, 0.0))
    val purchaserLocation: StateFlow<LatLng> = _purchaserLocation

    private val _recipientLocation = MutableStateFlow<LatLng>(LatLng(0.0, 0.0))
    val recipientLocation: StateFlow<LatLng> = _recipientLocation

    private val _purchaserMarkerExists = MutableStateFlow<Boolean>(false)
    val purchaserMarkerExists: StateFlow<Boolean> = _purchaserMarkerExists.asStateFlow()

    private val _recipientMarkerExists = MutableStateFlow<Boolean>(false)
    val recipientMarkerExists: StateFlow<Boolean> = _recipientMarkerExists.asStateFlow()

    private val _showFilteredMarkers = MutableStateFlow<Boolean>(false)
    val showFilteredMarkers: StateFlow<Boolean> = _showFilteredMarkers.asStateFlow()

    private val _setDarkMode = MutableStateFlow(false)
    val setDarkMode: StateFlow<Boolean> = _setDarkMode.asStateFlow()

    private val _setDynamicColor = MutableStateFlow(false)
    val setDynamicColor: StateFlow<Boolean> = _setDynamicColor.asStateFlow()


    private val _filteredMarkers = MutableStateFlow<MutableList<UserData>>(
        value = mutableListOf()
    )
    val filteredMarkers: StateFlow<MutableList<UserData>> = _filteredMarkers.asStateFlow()

    private val _nonFilteredMarkers = MutableStateFlow<MutableList<UserData>>(
        value = mutableListOf()
    )
    val nonFilteredMarkers: StateFlow<MutableList<UserData>> = _nonFilteredMarkers.asStateFlow()


    private val _purchaserToRecipientDistance = MutableStateFlow<Float?>(null)
    val purchaserToRecipientDistance: StateFlow<Float?> = _purchaserToRecipientDistance.asStateFlow()

    fun setDarkModeOn(darkOn: Boolean){
        _setDarkMode.value = darkOn
    }

    fun setDynamicColor(dynamicOn: Boolean){
        _setDynamicColor.value = dynamicOn
    }

    fun calculatepurchaserToRecipientDistance(purchaserLat: Double, purchaserLng: Double, recipientLat: Double, recipientLng: Double) {
        val purchaserLocation = LatLng(purchaserLat, purchaserLng)
        val recipientLocation = LatLng(recipientLat, recipientLng)

        val distance = SphericalUtil.computeDistanceBetween(purchaserLocation, recipientLocation)
        _purchaserToRecipientDistance.value = distance.toFloat()

    }

    fun setPurchaserLocation(location: LatLng) {
        _purchaserLocation.value = location
    }

    fun setRecipientLocation(location: LatLng) {
        _recipientLocation.value = location
    }



    fun setPurchaserMarkerExists(exists: Boolean) {
        _purchaserMarkerExists.value = exists
    }

    fun setRecipientMarkerExists(exists: Boolean) {
        _recipientMarkerExists.value = exists
    }

    fun setShowFilteredMarkers(show: Boolean, n: Int, recDataList: MutableList<UserData>) {
        _showFilteredMarkers.value = show

        when(showFilteredMarkers.value){
            true ->{
                _filteredMarkers.value.add(recDataList[n])

            }
            false ->{
                _nonFilteredMarkers.value.add(recDataList[n])
            }
        }
    }

    fun getCoordinates(context: Context, address: String, onResult: (LatLng) -> Unit) {
        val geocoder = Geocoder(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocationName(address, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    onResult(addresses.firstOrNull()?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(0.0, 0.0))
                }

                override fun onError(errorMessage: String?) {
                    Log.e("Geocoder", "Error: $errorMessage")
                    onResult(LatLng(0.0, 0.0))
                }
            })
        } else {
            try {
                val addresses = geocoder.getFromLocationName(address, 1)
                onResult(addresses?.firstOrNull()?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(0.0, 0.0))
            } catch (e: IOException) {
                onResult(LatLng(0.0, 0.0))
            }
        }
    }
}