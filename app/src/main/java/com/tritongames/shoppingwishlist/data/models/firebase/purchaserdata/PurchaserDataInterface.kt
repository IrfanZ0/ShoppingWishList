package com.tritongames.shoppingwishlist.data.models.firebase.purchaserdata

import android.content.Context

interface PurchaserDataInterface {
    suspend fun getPurchaserData(purchaserEmail: String, purchaserPassword: String): MutableList<PurchaserData>

    suspend fun addNewPurchaser(purchaserEmail:String, purchaserPassword: String)
    suspend fun savePurchaserData(pData: HashMap<String, String>, context: Context)
}