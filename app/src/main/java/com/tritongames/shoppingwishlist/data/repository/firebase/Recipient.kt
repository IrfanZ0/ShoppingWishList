package com.tritongames.shoppingwishlist.data.repository.firebase

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.tritongames.shoppingwishlist.data.models.firebase.recipientdata.RecipientData
import com.tritongames.shoppingwishlist.data.models.firebase.recipientdata.RecipientDataInterface
import com.tritongames.shoppingwishlist.presentation.MainActivity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Recipient @Inject constructor(): RecipientDataInterface {
    override suspend fun getRecipientData(
        recipientEmail: String,
        purchaserEmail: String
    ): MutableList<RecipientData> {
        val recipientList = mutableListOf<RecipientData>()
        
        try{
            val db = FirebaseFirestore.getInstance("shoppingwishlist")
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
                    val rData = RecipientData(
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
                    recipientList.add(rData)

                }

            }

            
        }catch(e: Exception){
            
        }
        return recipientList
    }

    override suspend fun saveRecipientData(
        pDataMap: HashMap<String, String>,
        rDataMap: HashMap<String, String>,
        context: Context
    ) {
        val db = FirebaseFirestore.getInstance("shoppingwishlist")
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