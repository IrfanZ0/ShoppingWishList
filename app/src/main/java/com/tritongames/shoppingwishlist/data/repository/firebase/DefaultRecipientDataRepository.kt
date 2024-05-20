package com.tritongames.shoppingwishlist.data.repository.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.tritongames.shoppingwishlist.data.models.firebase.RecipientData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultRecipientDataRepository @Inject constructor(): RecipientDataRepository {
    override suspend fun getAllRecipientData(email: String): List<RecipientData> {
        val recipientInfo: MutableList<RecipientData> = mutableListOf()
        try {
            val authorizedCurrentUser = Firebase.auth.currentUser
            if (authorizedCurrentUser != null && authorizedCurrentUser.email == email) {
                lateinit var rData: RecipientData
                val fb = Firebase.firestore
                val docRef = fb.collection("purchasers").document(email).collection("Recipients")
                val querySnapshot = docRef.get().await()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        rData = RecipientData(
                            document.data["purchaserImage"].toString(),
                            document.data["purchaserFirstName"].toString(),
                            document.data["purchaserLastName"].toString(),
                            document.data["purchaserAddress"].toString(),
                            document.data["purchaserCity"].toString(),
                            document.data["purchaserState"].toString(),
                            document.data["purchaserZipCode"].toString(),
                            document.data["purchaserEmail"].toString(),
                            document.data["purchaserPhone"].toString()
                        )

                    }


                    recipientInfo.add(rData)
                }
            }
            else {
                Log.d(TAG, "User has probably not signed in with correct email")
            }

        }catch(e: Exception) {
            Log.d(TAG, e.message.toString())
        }

        return recipientInfo.toList()
    }

    override suspend fun saveRecipientData(
        rDataMap: HashMap<String, String>,
        purchaserEmail: String,
        recipientEmail: String
    ) {
        try{
            val fb = Firebase.firestore
            val authorizedPurchaser = Firebase.auth.currentUser

            if(authorizedPurchaser != null && authorizedPurchaser.email == purchaserEmail) {
                fb.collection("purchasers").document(purchaserEmail).collection("Recipients").document(recipientEmail).set(rDataMap)
                    .addOnSuccessListener { Log.d(TAG, "Saving recipient Data was successful") }
                    .addOnFailureListener{e -> Log.w(TAG, "Error writing recipient data", e)}

            }

        }
        catch(e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }
}