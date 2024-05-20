package com.tritongames.shoppingwishlist.data.models.firebase

import com.google.firebase.Firebase

interface PurchaserDataInterface {
    suspend fun getPurchaserData(fireBase: Firebase): List<PurchaserData>
    suspend fun savePurchaserData(pData: HashMap<String, String>)
}