package com.tritongames.shoppingwishlist.data.models.firebase.login

import android.annotation.SuppressLint
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import com.tritongames.shoppingwishlist.util.SignInDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FirebaseAuthImpl @Inject constructor(): FirebaseInterface {


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

    override fun onSignUpClick(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String) {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            SignInDetails.Success(true)
        } catch (fae: FirebaseAuthException) {
            when (fae.errorCode) {
                "ERROR_WRONG_PASSWORD" -> SignInDetails.Error("Incorrect password. Please try again.")
                "ERROR_USER_NOT_FOUND" -> SignInDetails.Error("No account found with this email address.")
                "ERROR_INVALID_EMAIL" -> SignInDetails.Error("The email address is badly formatted.")
                "ERROR_USER_DISABLED" -> SignInDetails.Error("This user account has been disabled.")
                else -> SignInDetails.Error(fae.message ?: "Authentication failed")
            }
        } catch (ioe: IOException) {
            SignInDetails.Error("Couldn't send/recieve data ${ioe.toString()}")
        } catch (e: Exception) {
            SignInDetails.Error("An unexpected error occurred: ${e.message}")
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            if (result.user?.isEmailVerified == true && result.user?.email == email) {
                // SignUpDetails.Success(true) - This still does nothing without a state to update
            } else {
                // SignUpDetails.Error("Email is not verified")
            }
        }catch(ioe: IOException){
            // SignUpDetails.Error("Problem sending/and receiving data: ${ioe.toString()}")
        }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }

}
