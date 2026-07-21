package com.halalark.shared.data.models.firebase.recipientdata

import com.tritongames.shoppingwishlist.data.models.firebase.recipientdata.RecipientData

interface RecipientDataInterface {
    suspend fun getRecipientData(recipientEmail: String, purchaserEmail: String): RecipientData

    suspend fun getAllRecipientData(purchaserEmail: String): MutableList<RecipientData>
    suspend fun saveRecipientData(pDataMap: HashMap<String,String>, rDataMap: HashMap<String, String>)

}
