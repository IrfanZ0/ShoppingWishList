package com.tritongames.shoppingwishlist.util

sealed class Resource<T>(val data: T?, val errorMsg: String?){
    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(message: String) : Resource<T>(null, message)
}
