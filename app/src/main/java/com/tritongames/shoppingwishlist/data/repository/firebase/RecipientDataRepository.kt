package com.tritongames.shoppingwishlist.data.repository.firebase

import com.tritongames.shoppingwishlist.data.models.firebase.RecipientData

interface RecipientDataRepository {
    suspend fun getAllRecipientData(email: String): List<RecipientData>
    suspend fun saveRecipientData(rDataMap: HashMap<String, String>, purchaserEmail: String, recipientEmail: String)
}