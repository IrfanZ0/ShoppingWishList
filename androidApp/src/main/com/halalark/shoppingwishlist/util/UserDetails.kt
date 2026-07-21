package com.tritongames.shoppingwishlist.util
import com.tritongames.shoppingwishlist.data.UserData

sealed class UserDetails {

        data class Success(val userData: MutableList<UserData>) : UserDetails()
        data class Error(val message: String) : UserDetails()
        object Loading : UserDetails()
        object Idle : UserDetails()

}