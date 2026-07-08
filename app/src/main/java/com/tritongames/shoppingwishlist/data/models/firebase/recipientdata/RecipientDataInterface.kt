package com.tritongames.shoppingwishlist.data.models.firebase.recipientdata

import android.content.Context

interface RecipientDataInterface {
    suspend fun getRecipientData(recipientEmail: String, purchaserEmail: String): MutableList<RecipientData>
    suspend fun saveRecipientData(pDataMap: HashMap<String,String>, rDataMap: HashMap<String, String>, context: Context)

}