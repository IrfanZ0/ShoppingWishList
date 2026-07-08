package com.tritongames.shoppingwishlist.util

sealed class SignInDetails {
    data class Success (val loginSucess: Boolean): SignInDetails()
    data class Error (val loginError: String): SignInDetails()
    data object Loading: SignInDetails()
}