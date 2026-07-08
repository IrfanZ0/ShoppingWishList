package com.tritongames.shoppingwishlist.data.repository.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.tritongames.shoppingwishlist.data.models.firebase.purchaserdata.PurchaserData
import com.tritongames.shoppingwishlist.data.models.firebase.purchaserdata.PurchaserDataInterface
import com.tritongames.shoppingwishlist.presentation.MainActivity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Purchaser @Inject constructor(): PurchaserDataInterface {
    val TAG = "Purchaser"
    override suspend fun getPurchaserData(purchaserEmail: String, purchaserPassword: String): MutableList<PurchaserData> {
       val pDataList = mutableListOf<PurchaserData>()
        try {
            val fireBaseAuth = Firebase.auth

            // Attempt to sign in if no user is currently logged in or if email doesn't match
            if (fireBaseAuth.currentUser == null || fireBaseAuth.currentUser?.email != purchaserEmail) {
                fireBaseAuth.signInWithEmailAndPassword(purchaserEmail, purchaserPassword).await()
            }

                val db = FirebaseFirestore.getInstance("shoppingwishlist")

                val documentSnapshot =
                    db.collection("Purchasers").document(purchaserEmail).get().await()

                if (documentSnapshot.exists()) {
                    val data = documentSnapshot.data
                    if (data != null) {
                        val geoPoint = data["Location"] as? GeoPoint
                        val userData = PurchaserData(
                            purchaserImage = data["Image"]?.toString() ?: "",
                            firstName = data["First Name"]?.toString() ?: "",
                            lastName = data["Last Name"]?.toString() ?: "",
                            address = data["Address"]?.toString() ?: "",
                            city = data["City"]?.toString() ?: "",
                            purchaserState = data["State"]?.toString() ?: "",
                            zipCode = data["Zip Code"]?.toString() ?: "",
                            phone = data["Phone"]?.toString() ?: "",
                            email = data["Email"]?.toString() ?: "",
                            location = if (geoPoint != null) LatLng(
                                geoPoint.latitude,
                                geoPoint.longitude
                            ) else LatLng(0.0, 0.0)
                        )
                        pDataList.add(userData)
                    } else {
                        Log.d(TAG, "Data is null")

                    }
                } else {
                    Log.d(TAG, "Document Snapshot does not exist")

                }



        }catch (e: Exception) {
            Log.d(TAG, "Error: ${e.message}")
            throw e
        }
        return pDataList
    }

    override suspend fun addNewPurchaser(
        purchaserEmail: String,
        purchaserPassword: String
    ) {
        try{
            val firebaseAuth = Firebase.auth
            firebaseAuth.createUserWithEmailAndPassword(purchaserEmail, purchaserPassword).await()
            val context = this@Purchaser
           Log.d(TAG, "A new Purchaser has been added")

        }catch(e: Exception){
            Log.d(TAG, "Failure to add new Purchaser: ${e.message}")
        }
    }

    override suspend fun savePurchaserData(pData: HashMap<String, String>, context: Context) {
        val db = FirebaseFirestore.getInstance("shoppingwishlist")
        db.collection("Purchasers").document(pData["Email"].toString())
            .set(pData).await()
        Toast.makeText(
            context,
            "Sign up was successful. Welcome ${
                pData["First Name"].plus(" ").plus(pData["Last Name"])
            }",
            Toast.LENGTH_SHORT
        ).show()
        val goBackToLoginPageIntent = Intent(context, MainActivity::class.java)
        context.startActivity(goBackToLoginPageIntent)
    }
}