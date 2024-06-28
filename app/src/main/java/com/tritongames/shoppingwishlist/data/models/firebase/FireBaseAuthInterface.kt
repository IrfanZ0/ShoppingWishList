package com.tritongames.shoppingwishlist.data.models.firebase

import kotlinx.coroutines.flow.Flow

interface FireBaseAuthInterface {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    fun signInSuccess(): Boolean
    fun signUpSuccess(userEmail: String): Boolean
    fun signOutSuccess(): Boolean
     suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}