package com.tritongames.shoppingwishlist.data.models.contacts

import com.google.gson.annotations.SerializedName
import com.tritongames.shoppingwishlist.data.models.Wishes

data class ContactsDataClassItem(
    val ID: Int,
    val Address: List<Address>,
    @SerializedName("Client ID")
    val Client_ID: String,
    @SerializedName("Client Secret")
    val Client_Secret: String,
    val Email: String,
    @SerializedName("PassWord")
    val Pass_Word: String,
    @SerializedName("Ephermal Key")
    val Ephermal_Key: String,
    @SerializedName("Ephermal Secret")
    val Ephermal_Secret: String,
    @SerializedName("First Name")
    val First_Name: String,
    @SerializedName("Last Name")
    val Last_Name: String,
    val Wishes: List<Wishes>
)