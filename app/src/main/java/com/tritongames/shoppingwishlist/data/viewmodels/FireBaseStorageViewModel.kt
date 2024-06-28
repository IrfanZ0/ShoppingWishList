package com.tritongames.shoppingwishlist.data.viewmodels

import android.net.Uri
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await


class FireBaseStorageViewModel: ViewModel() {

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun GetPublicImages() {
        val imageList: MutableList<Uri> = mutableListOf()

        val fbStorage = Firebase.storage
        val fbPublicImages = fbStorage.reference.child("/public/images")
        LaunchedEffect(key1 = imageList) {
            val listResult = fbPublicImages?.listAll()?.await()
            if (listResult != null) {
                for (storeRef in listResult.items) {
                    val uri = storeRef.downloadUrl.await()
                    imageList.add(uri)
                }
            }

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(5)
        ) {
            var imageUrlString = ""
            items(imageList.size) {
                val imageCount = it
                ElevatedButton(onClick = { }) {
                    GlideImage(
                        model = imageList[imageCount].toString(),
                        contentDescription = imageList[imageCount].toString()
                    )

                }

            }

        }

    }
}

private fun FirebaseApp?.get(storage: FirebaseStorage): Any {
    return Firebase.storage

}
