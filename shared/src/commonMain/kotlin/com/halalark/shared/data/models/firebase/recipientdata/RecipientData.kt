package com.tritongames.shoppingwishlist.data.models.firebase.recipientdata

import com.google.android.gms.maps.model.LatLng

data class RecipientData(
    val recipientImage: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val city: String,
    val recipientState: String,
    val zipCode: String,
    val email: String,
    val phone: String,
    val location: LatLng

)
