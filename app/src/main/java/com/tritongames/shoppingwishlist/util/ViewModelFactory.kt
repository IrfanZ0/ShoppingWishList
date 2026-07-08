package com.tritongames.shoppingwishlist.util

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tritongames.shoppingwishlist.data.repository.bestbuy.BestBuyRepository
import com.tritongames.shoppingwishlist.data.repository.category.ShopCategoryRepository
import com.tritongames.shoppingwishlist.data.repository.userPreferences.UserPreferenceRepository
import com.tritongames.shoppingwishlist.data.viewmodels.BestBuyViewModel
import com.tritongames.shoppingwishlist.data.viewmodels.ShoppingDataViewModel
import kotlinx.coroutines.CoroutineDispatcher

@Suppress("UNCHECKED_CAST")
class BBViewModelFactory(private val bbRepository: BestBuyRepository, private val userPreferenceRepository: UserPreferenceRepository, private val dispatchersIO: CoroutineDispatcher):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass){

        lateinit var vm: T
        when{
            isAssignableFrom(BestBuyViewModel::class.java) -> BestBuyViewModel(bbRepository, userPreferenceRepository, dispatchersIO)

            else -> { throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")}
        }


    } as T
   
}
//
//@Suppress("UNCHECKED_CAST")
//class FirebasePurchaserViewModelFactory(private val fbPurchaserRepo: PurchaserDataRepository, private val dispatcher: DispatcherProvider):
//    ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        with(modelClass) {
//
//            lateinit var vm: T
//            when {
//                isAssignableFrom(FirebasePurchaserViewModel::class.java) -> FirebasePurchaserViewModel(
//                   fbPurchaserRepo,
//                    dispatcher
//                )
//
//                else -> {
//                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//                }
//            }
//
//
//        } as T
//}

@Suppress("UNCHECKED_CAST")
class ShopDataViewModelFactory(private val shopCatRepo: ShopCategoryRepository, private val dispatcherIO: CoroutineDispatcher, private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {

            lateinit var vm: T
            when {
                isAssignableFrom(ShoppingDataViewModel::class.java) -> ShoppingDataViewModel(
                    shopCatRepo,
                    dispatcherIO,
                    dataStore
                )

                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }


        } as T
}









