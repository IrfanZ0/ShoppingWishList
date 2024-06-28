package com.tritongames.shoppingwishlist.data.repository.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData
import com.tritongames.shoppingwishlist.data.viewmodels.FirebasePurchaserViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultPurchaserDataRepository @Inject constructor(
    private val mainDispatchers: MainCoroutineDispatcher,
    private val ioDispatchers: CoroutineDispatcher
): PurchaserDataRepository {
    val TAG = "DefaultPurchaserDataRepository"

    override suspend fun getPurchaserData(email: String) = coroutineScope {
        val purchaserInfo: MutableList<PurchaserData> = mutableListOf()
        val authorizedCurrentUser = Firebase.auth.currentUser
        var pData = PurchaserData(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
        val purchaserRepo: PurchaserDataRepository = DefaultPurchaserDataRepository(mainDispatchers, ioDispatchers)
        withContext(ioDispatchers) {
            if (authorizedCurrentUser != null && authorizedCurrentUser.email == email) {
                val fb = Firebase.firestore
                val docRef = async { fb.collection("purchasers").document(email) }
                val docReference = docRef.await()
                docReference.get().addOnSuccessListener {
                    pData = PurchaserData(
                        it.data?.get("purchaserImage").toString(),
                        it.data?.get("purchaserFirstName").toString(),
                        it.data?.get("purchaserLastName").toString(),
                        it.data?.get("purchaserAddress").toString(),
                        it.data?.get("purchaserCity").toString(),
                        it.data?.get("stateOfPurchaser").toString(),
                        it.data?.get("purchaserZipCode").toString(),
                        it.data?.get("purchaserEmail").toString(),
                        it.data?.get("purchaserPhone").toString(),
                        it.data?.get("purchaserUserName").toString(),
                        it.data?.get("purchaserPassword").toString()
                    )

                    val fbPurchaserVM = FirebasePurchaserViewModel(purchaserRepo, mainDispatchers, ioDispatchers)
                    fbPurchaserVM.writePurchaserState(pData)

                }
            } else {
                Log.d(TAG, "User has probably not signed in with correct email")
            }
        }
        withContext(mainDispatchers) {


        }

    }

    override suspend fun savePurchaserData(pDataMap: HashMap<String, String>, email: String) {

            val fb = Firebase.firestore
            val authorizedPurchaser = Firebase.auth.currentUser

            withContext(ioDispatchers) {
                if(authorizedPurchaser != null && authorizedPurchaser.email == email) {
                    fb.collection("purchasers").document(email).set(pDataMap)
                        .addOnSuccessListener { Log.d(TAG, "Saving Purchaser Data was successful") }
                        .addOnFailureListener{e -> Log.w(TAG, "Error writing purchaser data", e)}
                }

            }

    }
}
