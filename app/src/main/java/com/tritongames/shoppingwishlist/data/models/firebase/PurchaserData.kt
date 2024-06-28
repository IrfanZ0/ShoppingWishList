package com.tritongames.shoppingwishlist.data.models.firebase

data class PurchaserData(
    var purchaserImage: String,
    var firstName: String,
    var lastName: String,
    var address: String,
    var city: String,
    var purchaserState: String,
    var zipCode: String,
    var email: String,
    var phone: String,
    val userName: String,
    var passWord: String
)


