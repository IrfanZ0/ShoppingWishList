package com.tritongames.shoppingwishlist.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tritongames.shoppingwishlist.data.models.firebase.FirebaseAuthImpl
import com.tritongames.shoppingwishlist.data.viewmodels.FireBaseSignInViewModel

@Suppress("UNCHECKED_CAST")
class FireBaseSignInViewModelFactory(private val firebaseAuthImpl: FirebaseAuthImpl): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass){

            lateinit var vm: T
            when{
                isAssignableFrom(FireBaseSignInViewModel::class.java) -> FireBaseSignInViewModel(firebaseAuthImpl)

                else -> { throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")}
            }


        } as T

}