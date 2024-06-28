package com.tritongames.shoppingwishlist.data.viewmodels

import android.util.Log
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.tritongames.shoppingwishlist.data.models.firebase.PurchaserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class PurchaserViewModel: ViewModel() {
    private val TAG = "PurchaserViewModel"
    private val _uiPurchaserState = MutableStateFlow(PurchaserData(
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
    )
    val uiPurchaserState: StateFlow<PurchaserData> = _uiPurchaserState.asStateFlow()

    private val _dayNightState = mutableStateOf<Boolean>(false)
    val dayNightState: State<Boolean> = _dayNightState

    fun changePurchaserImage(purchaserImage: String) {
        _uiPurchaserState.value.purchaserImage = purchaserImage
    }


    fun changeDayNightMode(isOn: Boolean) {
        _dayNightState.value = isOn
    }
    fun changePurchaserInfo(pInfo: MutableList<String>) {
        _uiPurchaserState.value.copy(
            pInfo[0],
            pInfo[1],
            pInfo[2],
            pInfo[3],
            pInfo[4],
            pInfo[5],
            pInfo[6],
            pInfo[7],
            pInfo[8],
            pInfo[9],
            pInfo[10]


        )
    }

    fun savePurchaserValues(data: PurchaserData) {
        val loggedInEmail = Firebase.auth.currentUser?.email
        val fb = Firebase.firestore
        fb.collection("purchasers")
            .document(loggedInEmail.toString())
            .set(data)
            .addOnSuccessListener {
               Log.d(TAG, "Document snapshot has been updated")
            }
            .addOnFailureListener{e ->Log.w(TAG, "Error updating document", e)}
    }

    fun readPurchaserValues() {
        val authorizedUser = Firebase.auth.currentUser
        var loggedInEmail: String = ""
        loggedInEmail = authorizedUser?.email?.toString() ?: _uiPurchaserState.value.email

        val fb = Firebase.firestore
        fb.collection("purchasers")
            .document(loggedInEmail)
            .get()
            .addOnSuccessListener { it ->
                Log.d(TAG, "Document snapshot has been found")
                if (it.data != null) {
                    _uiPurchaserState.value.purchaserImage = it.data!!["purchaserImage"].toString()
                    _uiPurchaserState.value.firstName = it.data!!["purchaserFirstName"].toString()
                    _uiPurchaserState.value.lastName = it.data!!["purchaserLastName"].toString()
                    _uiPurchaserState.value.address = it.data!!["purchaserAddress"].toString()
                    _uiPurchaserState.value.city = it.data!!["purchaserCity"].toString()
                    _uiPurchaserState.value.purchaserState = it.data!!["purchaserState"].toString()
                    _uiPurchaserState.value.zipCode = it.data!!["purchaserZipCode"].toString()
                    _uiPurchaserState.value.email = it.data!!["purchaserEmail"].toString()
                    _uiPurchaserState.value.phone = it.data!!["purchaserPhone"].toString()
                    _uiPurchaserState.value.passWord = it.data!!["purchaserPassword"].toString()


                }
            }
            .addOnFailureListener{e ->Log.w(TAG, "Error reading document", e)}
    }
    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun SetImages(image: String) {
        val requestManager = Glide.with(LocalContext.current)

        Log.d("PurchaserViewModel", "Image Loaded: $image")
        ElevatedButton(onClick = { changePurchaserImage(image) }) {
            GlideImage (
                image,
                "Image"

            ) {
                it.thumbnail(
                    requestManager
                        .asDrawable()
                        .load(image)
                )
            }

            }


    }


    @Composable

    fun getPublicImages(userEmail: String?): MutableList<String> {
        val fbStorage = Firebase.storage
        val imageList: MutableList<String> = mutableListOf()
        LaunchedEffect(
            key1 = userEmail,
            block =
            {
                if (userEmail == null) {

                    val imagesRef = fbStorage.reference.child("public/media/images")

                    val listResult = imagesRef.listAll().await()


                    for (storRef in listResult.items) {
                        val urlString = storRef.downloadUrl.await()
                        Log.d(TAG, urlString.toString())
                        imageList.add(urlString.toString())
                    }
                }
                else {
                        val imagesRef = fbStorage.reference.child("purchasers/$userEmail/media/public/images")

                        val listResult = imagesRef.listAll().await()

                        for (storRef in listResult.items) {
                            val urlString = storRef.downloadUrl.await()
                            imageList.add(urlString.toString())
                        }

                }
            }
        )
       return imageList

    }


}