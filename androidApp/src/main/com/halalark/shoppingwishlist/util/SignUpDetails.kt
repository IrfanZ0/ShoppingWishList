package com.tritongames.shoppingwishlist.util

sealed class SignUpDetails {
    data class Success(val signUpSuccess: Boolean): SignUpDetails()
    data class Error(val signUpError: String): SignUpDetails()
    data object Loading: SignUpDetails()

}