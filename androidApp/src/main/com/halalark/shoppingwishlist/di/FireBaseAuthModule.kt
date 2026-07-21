package com.tritongames.shoppingwishlist.di

import com.tritongames.shoppingwishlist.data.models.firebase.login.FirebaseAuthImpl
import com.tritongames.shoppingwishlist.data.models.firebase.login.FirebaseInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FireBaseAuthModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseInterface(
        firebaseAuthImpl: FirebaseAuthImpl
    ): FirebaseInterface
}
