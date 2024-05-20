package com.tritongames.shoppingwishlist.data.models.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthImpl @Inject constructor(): FirebaseInterface {

    private var loginSuccess: Boolean = false
    override val currentUser: Flow<User>
        @SuppressLint("RestrictedApi")
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    auth.currentUser?.let { User(it.email) }?.let { this.trySend(it) }
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserEmail: String
        get() = Firebase.auth.currentUser?.email.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun signInSuccess(): Boolean {
        return loginSuccess
    }

    override fun signUpSuccess(): Boolean {
        TODO("Not yet implemented")
    }

    override fun signOutSuccess(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            loginSuccess = if (task.isSuccessful) {
                Log.d("FireBaseAuthImpl", "Sign in with email and password was successful")

                true
            } else {
                Log.d("FireBaseAuthImpl", "Sign in with email and password was not successful")
                false
            }

        }


    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }

}