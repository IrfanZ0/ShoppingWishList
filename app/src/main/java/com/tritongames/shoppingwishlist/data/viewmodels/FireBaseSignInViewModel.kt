package com.tritongames.shoppingwishlist.data.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.firebase.FirebaseAuthImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class FireBaseSignInViewModel @Inject constructor(private val fireBaseAuthImpl: FirebaseAuthImpl): ViewModel() {
    private var email = MutableStateFlow("")
    private var password = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(): Boolean {
        var signedIn = false
        viewModelScope.launch {
            fireBaseAuthImpl.signIn(email.value, password.value)
            if (fireBaseAuthImpl.signInSuccess()) {
                signedIn = true
                Log.d("FireBaseSignInViewModel", "Sign in successful, ${fireBaseAuthImpl.signInSuccess()}")
            }

        }
        Log.d("FireBaseSignInViewModel", "Sign in successful, ${fireBaseAuthImpl.signInSuccess()}")
        return signedIn
    }

    fun onSignUpClick(): Boolean {
        var signedUp by Delegates.notNull<Boolean>()
        viewModelScope.launch {

            fireBaseAuthImpl.signUp(email.value, password.value)
            if (fireBaseAuthImpl.signUpSuccess()) {
                signedUp = true
                Log.d("FireBaseSignInViewModel", "Sign up successful, ${fireBaseAuthImpl.signUpSuccess()}")
            }
            else {
                signedUp = false
            }

        }
        Log.d("FireBaseSignInViewModel", "Sign up unsuccessful, ${fireBaseAuthImpl.signUpSuccess()}")
        return signedUp
    }

}