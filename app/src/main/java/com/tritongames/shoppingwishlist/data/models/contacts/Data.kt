package com.tritongames.shoppingwishlist.data.models.contacts

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Address")
    val address: String,
    @SerializedName("City")
    val city: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("First Name")
    val firstname: String,
    @SerializedName("ID")
    val id: String,
    @SerializedName("Last Name")
    val lastname: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Phone")
    val phone: String,
    @SerializedName("State")
    val state: String,
    @SerializedName("UserName")
    val username: String,
    @SerializedName("Zip Code")
    val zipcode: String
)