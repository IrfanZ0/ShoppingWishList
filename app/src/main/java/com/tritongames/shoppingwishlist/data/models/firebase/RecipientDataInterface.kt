package com.tritongames.shoppingwishlist.data.models.firebase

import com.google.firebase.Firebase

interface RecipientDataInterface {
    suspend fun getRecipientData(firebase: Firebase): List<RecipientData>
    suspend fun saveRecipientData(rDataMap: HashMap<String, String>)

}