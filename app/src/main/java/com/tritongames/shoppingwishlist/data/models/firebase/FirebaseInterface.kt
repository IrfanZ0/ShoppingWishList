package com.tritongames.shoppingwishlist.data.models.firebase

import android.annotation.SuppressLint
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow

@SuppressLint("RestrictedApi")
interface FirebaseInterface {
    val currentUser: Flow<User>
    val currentUserEmail: String
    fun hasUser(): Boolean
    fun signInSuccess(): Boolean
    fun signUpSuccess(): Boolean
    fun signOutSuccess(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()

}