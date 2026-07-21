package com.tritongames.shoppingwishlist.data.models

import com.google.android.gms.maps.model.LatLng

data class Places(
    val name : String,
    val address : LatLng,
    val latlng : LatLng,
    val phone : String
)
