package com.tritongames.shoppingwishlist.data.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tritongames.shoppingwishlist.data.models.firebase.FirebaseAuthImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            }

        }
        return signedIn
    }

    fun onSignUpClick(): Boolean {
        var signedUp = false
        viewModelScope.launch {
            fireBaseAuthImpl.signUp(email.value, password.value)
            if (fireBaseAuthImpl.signUpSuccess()) {
                signedUp = true
            }
        }
        return signedUp
    }

}