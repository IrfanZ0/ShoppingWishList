package com.tritongames.shoppingwishlist.data.repository.firebase

interface PurchaserDataRepository {

    suspend fun getPurchaserData(email: String)
    suspend fun savePurchaserData(pData: HashMap<String, String>, email: String)
}