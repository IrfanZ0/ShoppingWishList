package com.tritongames.shoppingwishlist.presentation

import android.annotation.SuppressLint
import android.content.Context
import com.google.ar.sceneform.rendering.ModelRenderable

interface Merchandise3D {




    @SuppressLint("SuspiciousIndentation")
    fun get3DModel(file: Int, context: Context): ModelRenderable


}