package com.tritongames.shoppingwishlist.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

class GlideImageHelper {
    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun ImageSelectorBox(
        imageUrl: String,
        isSelected: Boolean,
        onSelectedChange: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        // 1. Define border and background based on selection state
        val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
        val borderWidth = if (isSelected) 3.dp else 0.dp

        Card(
            modifier = modifier
                .size(120.dp) // Square aspect ratio for the box
                .padding(4.dp)
                .clickable { onSelectedChange() },
            shape = RoundedCornerShape(12.dp),
            border = if (isSelected) BorderStroke(borderWidth, borderColor) else null,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            // 2. Use GlideImage to load the network photo
            GlideImage(
                model = imageUrl,
                contentDescription = "Selectable Photo",
                contentScale = ContentScale.Crop, // Crops nicely into the square box
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                // Optional: Show a loading placeholder or error fallback
                loading = placeholder(android.R.drawable.progress_horizontal),
                failure = placeholder(android.R.drawable.stat_notify_error)
            )
        }
    }
}