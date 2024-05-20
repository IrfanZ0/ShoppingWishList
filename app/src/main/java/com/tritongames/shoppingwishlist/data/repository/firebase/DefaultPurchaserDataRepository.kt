package com.tritongames.shoppingwishlist.data.repository.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultPurchaserDataRepository @Inject constructor(): PurchaserDataRepository {
    val TAG = "DefaultPurchaserDataRepository"
    companion object {

    }
    override suspend fun getPurchaserData(email: String): List<PurchaserData> {
        val purchaserInfo: MutableList<PurchaserData> = mutableListOf()
        try {
            val authorizedCurrentUser = Firebase.auth.currentUser
            if (authorizedCurrentUser != null && authorizedCurrentUser.email == email) {
                val fb = Firebase.firestore
                val docRef = fb.collection("purchasers").document(email)
                val docSnapshot = docRef.get().await()
                if (docSnapshot != null) {

                    val pData = PurchaserData(
                        docSnapshot.data?.get("purchaserImage").toString(),
                        docSnapshot.data?.get("purchaserFirstName").toString(),
                        docSnapshot.data?.get("purchaserLastName").toString(),
                        docSnapshot.data?.get("purchaserAddress").toString(),
                        docSnapshot.data?.get("purchaserCity").toString(),
                        docSnapshot.data?.get("purchaserState").toString(),
                        docSnapshot.data?.get("purchaserZipCode").toString(),
                        docSnapshot.data?.get("purchaserEmail").toString(),
                        docSnapshot.data?.get("purchaserPhone").toString(),
                        docSnapshot.data?.get("purchaserUserName").toString(),
                        docSnapshot.data?.get("purchaserPassword").toString()


                    )
                    purchaserInfo.add(pData)
                }
            }
            else {
                Log.d(TAG, "User has probably not signed in with correct email")
            }

        }catch(e: Exception) {
            Log.d(TAG, e.message.toString())
        }

        return purchaserInfo.toList()
    }

    override suspend fun savePurchaserData(pDataMap: HashMap<String, String>, email: String) {
        try{
            val fb = Firebase.firestore
            val authorizedPurchaser = Firebase.auth.currentUser

            if(authorizedPurchaser != null && authorizedPurchaser.email == email) {
                fb.collection("purchasers").document(email).set(pDataMap)
                    .addOnSuccessListener { Log.d(TAG, "Saving Purchaser Data was successful") }
                    .addOnFailureListener{e -> Log.w(TAG, "Error writing purchaser data", e)}

            }

        }
        catch(e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }
}
