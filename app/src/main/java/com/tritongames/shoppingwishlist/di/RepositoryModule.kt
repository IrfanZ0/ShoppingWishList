package com.tritongames.shoppingwishlist.di

import com.tritongames.shoppingwishlist.data.repository.bestbuy.BestBuyRepository
import com.tritongames.shoppingwishlist.data.repository.bestbuy.BestBuyRepositoryImpl
import com.tritongames.shoppingwishlist.data.repository.catalog.ProductsCatalogRepository
import com.tritongames.shoppingwishlist.data.repository.catalog.ProductsCatalogRepositoryImpl
import com.tritongames.shoppingwishlist.data.repository.category.ShopCategoryRepository
import com.tritongames.shoppingwishlist.data.repository.category.ShopCategoryRepositoryImpl
import com.tritongames.shoppingwishlist.data.repository.contacts.ContactsRepository
import com.tritongames.shoppingwishlist.data.repository.contacts.ContactsRepositoryImpl
import com.tritongames.shoppingwishlist.data.repository.userPreferences.UserPreferenceRepository
import com.tritongames.shoppingwishlist.data.repository.userPreferences.UserPreferenceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserPreferenceRepository(userPrefRepoImpl: UserPreferenceRepositoryImpl): UserPreferenceRepository


    @Singleton
    @Binds
    abstract fun bindContactsRepository(contactRepo: ContactsRepositoryImpl): ContactsRepository


    @Singleton
    @Binds
    abstract fun bindShopCategoryRepository(shopCategoryRepo: ShopCategoryRepositoryImpl): ShopCategoryRepository

    @Singleton
    @Binds
    abstract fun bindBestBuyRepository(bestBuyRepo: BestBuyRepositoryImpl): BestBuyRepository

    @Singleton
    @Binds
    abstract fun bindProductsCatalogRespository(prodRepo: ProductsCatalogRepositoryImpl): ProductsCatalogRepository

}