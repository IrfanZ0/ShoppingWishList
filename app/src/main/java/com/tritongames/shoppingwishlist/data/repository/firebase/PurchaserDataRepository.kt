package com.tritongames.shoppingwishlist.data.repository.firebase

import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData

interface PurchaserDataRepository {
    companion object {

    }
    suspend fun getPurchaserData(email: String): List<PurchaserData>
    suspend fun savePurchaserData(pData: HashMap<String, String>, email: String)
}