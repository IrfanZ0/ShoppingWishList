package com.tritongames.shoppingwishlist.presentation

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shoppingwishlist.R
import javax.inject.Inject

class MerchandiseImagesAdapter @Inject constructor(private val merchImageList:MutableList<String>) : RecyclerView.Adapter<MerchandiseImagesAdapter.RecyclerViewHolder>(){

    private var pos: Int = 0

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val imageView: ImageView = itemView.findViewById(R.id.item_image)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.mechandise_layout, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {

        val merchDrawables = Drawable.createFromPath(merchImageList[position])
        holder.imageView.setImageDrawable(merchDrawables)
        pos = holder.oldPosition
    }

    override fun getItemCount(): Int {
        return merchImageList.size

    }


}