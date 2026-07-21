package com.halalark.shared.data.models.firebase.purchaserdata


interface PurchaserDataInterface {
    suspend fun getPurchaserData(purchaserEmail: String, purchaserPassword: String): MutableList<PurchaserData>

    suspend fun addNewPurchaser(purchaserEmail:String, purchaserPassword: String)
    suspend fun savePurchaserData(pData: HashMap<String, String>)
}
