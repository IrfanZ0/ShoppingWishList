package com.tritongames.shoppingwishlist.data.models.contacts

import com.google.gson.annotations.SerializedName

data class ContactsDataClassItem(
    @SerializedName("ID")
    val id: Int,
//    @SerializedName("Address")
//    val address: Address,
    @SerializedName("Client ID")
    val clientId: String,
    @SerializedName("Client Secret")
    val clientsecret: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("PassWord")
    val password: String,
    @SerializedName("Ephermal Key")
    val ephermalkey: String,
    @SerializedName("Ephermal Secret")
    val ephermalsecret: String,
    @SerializedName("First Name")
    val firstname: String,
    @SerializedName("Last Name")
    val lastname: String,
    @SerializedName("Phone")
    val phone: String,

)