package com.tritongames.shopwishlist.di

import android.app.Application
import android.content.Context
import android.text.TextUtils
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.tritongames.shopwishlist.data.ShoppingData
import com.tritongames.shopwishlist.data.WishesApi
import com.tritongames.shopwishlist.data.models.ShoppingCategoriesInterface
import com.tritongames.shopwishlist.data.repository.*
import com.tritongames.shopwishlist.util.AuthenticationInterceptor
import com.tritongames.shopwishlist.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


public const val BASE_URL = "https://api.github.com"
private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
 val appContext: Context? = ShoppingData.context
private const val USER_PREFERENCES = "user_preferences"
private lateinit var retrofit: Retrofit
private val builder: Retrofit.Builder = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    object Services{
        fun <S> createService(serviceClass: Class<S>, userName: String?, passWord: String?): S{
            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)){
                val authToken: String? = userName?.let { okhttp3.Credentials.basic(it, passWord!!) }
                return createService(serviceClass, authToken)
            }
            return createService(serviceClass, null, null)

        }

        fun <S> createService(
            serviceClass: Class<S>, authToken: String?
        ): S {
            if (!TextUtils.isEmpty(authToken)) {
                val interceptor = authToken?.let { AuthenticationInterceptor(it) }
                if (!interceptor?.let {
                        httpClient.interceptors().contains(
                            it
                        )
                    }!!) {
                    httpClient.addInterceptor(interceptor)
                    retrofit = builder.client(httpClient.build()).build()

                }
            }
            return serviceClass.let { retrofit.create(it) }
        }


    }

    @Singleton
    @Provides
    fun provideWishesApi() : WishesApi {
        return Retrofit.Builder()
            .baseUrl((BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WishesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideShoppingCategoriesApi() : ShoppingCategoriesInterface {
        return Retrofit.Builder()
            .baseUrl((BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShoppingCategoriesInterface::class.java)
    }


    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Singleton
    @Provides
    fun provideUserPreferenceRepository(dataStore: DataStore<Preferences>): UserPreferenceRepository{
        return UserPreferenceRepositoryImpl(dataStore)
    }

    @Singleton
    @Provides
    fun provideRepository(api: WishesApi, app: Application): WishesRecipientRepository {
        return WishesRecipientRepositoryImpl(api, app)
    }

    @Singleton
    @Provides
    fun provideShopCategoryRepository(api: ShoppingCategoriesInterface, app: Application): ShopCategoryRepository {
        return ShopCategoryRepositoryImpl(api, app)
    }

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}


