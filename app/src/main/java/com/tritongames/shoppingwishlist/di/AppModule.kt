package com.tritongames.shoppingwishlist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.tritongames.shoppingwishlist.data.models.bestbuy.BestBuyInterface
import com.tritongames.shoppingwishlist.data.models.catalog.CatalogInterface
import com.tritongames.shoppingwishlist.data.models.categories.ShoppingCategoriesInterface
import com.tritongames.shoppingwishlist.data.models.checkout.StripeCustomerApi
import com.tritongames.shoppingwishlist.data.models.checkout.StripeInterface
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://apis.androidparadise.site"
const val BEST_BUY_BASE_URL = "https://api.bestbuy.com"
const val userPreferences = "user_preferences"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @dagger.Provides
    fun provideShoppingCategoriesApi() : ShoppingCategoriesInterface {
        return Retrofit.Builder()
            .baseUrl((BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShoppingCategoriesInterface::class.java)
    }

    @Singleton
    @dagger.Provides
    fun provideProductCatalogApi() : CatalogInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatalogInterface::class.java)
    }


    @Singleton
    @dagger.Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<androidx.datastore.preferences.core.Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, userPreferences)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(userPreferences) }

        )
    }

    val Context.datastore by preferencesDataStore(
        name = DataStorePreferenceStorage.PREFS_NAME,
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(
                    context,
                    DataStorePreferenceStorage.PREFS_NAME
                )
            )
        }
    )


    @Singleton
    @dagger.Provides
    fun provideBestBuyInterface(): BestBuyInterface{
        return Retrofit.Builder()
            .baseUrl(BEST_BUY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BestBuyInterface::class.java)

    }


    @Singleton
    @dagger.Provides
    fun provideCheckoutInterface(): StripeInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StripeInterface::class.java)

    }

    @Singleton
    @dagger.Provides
    fun provideCustomerCheckoutInterface(): StripeCustomerApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StripeCustomerApi::class.java)

    }

    @Singleton
    @dagger.Provides
    fun provideCoroutineDispatcherMain(): MainCoroutineDispatcher {
        return Dispatchers.Main
    }

    @Singleton
    @dagger.Provides
    fun provideCoroutineDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }


}

