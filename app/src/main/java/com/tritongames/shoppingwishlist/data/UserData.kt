package com.tritongames.shoppingwishlist.data

import com.google.android.gms.maps.model.LatLng

data class UserData(
    var image: String = "",
    var firstname: String = "",
    var lastname: String = "",
    var address: String = "",
    var city: String = "",
    var state: String = "",
    var zip: String = "",
    var phone: String = "",
    var email: String = "",
    var role: String = "",
    var location: LatLng = LatLng(0.0, 0.0)
)

