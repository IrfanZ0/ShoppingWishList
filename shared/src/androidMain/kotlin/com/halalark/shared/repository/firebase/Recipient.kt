package com.tritongames.shoppingwishlist.data.repository.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.tritongames.shoppingwishlist.data.models.firebase.recipientdata.RecipientData
import com.halalark.shared.data.models.firebase.recipientdata.RecipientDataInterface
import com.tritongames.shoppingwishlist.presentation.MainActivity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.get
import kotlin.text.iterator

class Recipient @Inject constructor(): RecipientDataInterface {
    val TAG = "Recipient"
    override suspend fun getRecipientData(
        recipientEmail: String,
        purchaserEmail: String
    ): RecipientData {
        var rData = RecipientData(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            LatLng(0.0, 0.0)
        )

        try{
            val db = FirebaseFirestore.getInstance("com.halalark.shoppingwishlist")
            val documentSnapshot = db.collection("Purchasers")
                .document(purchaserEmail)
                .collection("Recipients")
                .document(recipientEmail)
                .get()
                .await()

            if(documentSnapshot.exists()){
                val data = documentSnapshot.data
                if (data != null){
                    val geoPoint = data["Location"] as GeoPoint
                    rData = RecipientData(
                        recipientImage = data["Image"] as String,
                        firstName = data["First Name"] as String,
                        lastName = data["Last Name"] as String,
                        address = data["Address"] as String,
                        city = data["City"] as String,
                        recipientState = data["State"] as String,
                        zipCode = data["Zip Code"] as String,
                        email = data["Email"] as String,
                        phone = data["Phone"] as String,
                        location = LatLng(
                            geoPoint.latitude,
                            geoPoint.longitude
                        )

                    )


                }


            }

        }catch(e: Exception){
            Log.d(TAG, "Error finding recipient, ${recipientEmail}: ${e.message}")

        }
        return rData
    }

    override suspend fun getAllRecipientData(purchaserEmail: String): MutableList<RecipientData> {
        var rData = RecipientData(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            LatLng(0.0, 0.0)
        )
        var rDataList: MutableList<RecipientData> = mutableListOf(rData)

        try {
            val db = FirebaseFirestore.getInstance("com.halalark.shoppingwishlist")
            val querySnapshot = db.collection("Purchasers")
                .document(purchaserEmail)
                .collection("Recipients")
                .get()
                .await()

            for (document in querySnapshot.documents) {
                val data = document.data
                if (data != null) {
                    val geoPoint = data["Location"] as? GeoPoint
                    val recipient = RecipientData(
                        recipientImage = data["Image"] as? String ?: "",
                        firstName = data["First Name"] as? String ?: "",
                        lastName = data["Last Name"] as? String ?: "",
                        address = data["Address"] as? String ?: "",
                        city = data["City"] as? String ?: "",
                        recipientState = data["State"] as? String ?: "",
                        zipCode = data["Zip Code"] as? String ?: "",
                        email = data["Email"] as? String ?: "",
                        phone = data["Phone"] as? String ?: "",
                        location = if (geoPoint != null) {
                            LatLng(geoPoint.latitude, geoPoint.longitude)
                        } else {
                            LatLng(0.0, 0.0)
                        }
                    )
                    rDataList.add(recipient)


                }

            }

        } catch (e: Exception) {
            Log.d(TAG, "Error finding any recipients: ${e.message}")

        }
        return rDataList
    }

    override suspend fun saveRecipientData(
        pDataMap: HashMap<String, String>,
        rDataMap: HashMap<String, String>,
        context: Context
    ) {
        val db = FirebaseFirestore.getInstance("com.halalark.shoppingwishlist")
        db.collection(
            "Purchasers")
            .document(rDataMap["Email"].toString())
            .collection("Recipients")
            .document(rDataMap["Email"].toString())
            .set(rDataMap)
        Toast.makeText(
            context,
            "Sign up was successful. Welcome ${
                rDataMap["First Name"].plus(" ").plus(rDataMap["Last Name"])
            }",
            Toast.LENGTH_SHORT
        ).show()
        val goBackToLoginPageIntent = Intent(context, MainActivity::class.java)
        context.startActivity(goBackToLoginPageIntent)
    }
}
