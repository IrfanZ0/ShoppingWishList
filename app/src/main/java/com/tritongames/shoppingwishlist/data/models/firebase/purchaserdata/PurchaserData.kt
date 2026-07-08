
package com.tritongames.shoppingwishlist.data.models.firebase.purchaserdata

import com.google.android.gms.maps.model.LatLng

data class PurchaserData(
    val purchaserImage: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val city: String,
    val purchaserState: String,
    val zipCode: String,
    val email: String,
    val phone: String,
    val location: LatLng,

    )


