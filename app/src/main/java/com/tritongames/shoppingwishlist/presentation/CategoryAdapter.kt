package com.tritongames.shoppingwishlist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.models.categories.ShoppingCategoriesDataClass
import javax.inject.Inject

class CategoryAdapter @Inject constructor(private var categoryList: ArrayList<ShoppingCategoriesDataClass>): RecyclerView.Adapter<CategoryAdapter.RecylerViewHolder>() {
    class RecylerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var tv: TextView = itemView.findViewById(R.id.categoriesText)
        var iv: ImageView = itemView.findViewById(R.id.categoriesPic)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecylerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.wish_layout, parent)
        return RecylerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecylerViewHolder, position: Int) {

        holder.tv.text = categoryList[position].category
        holder.iv.setImageResource(categoryList[position].image)
    }

    override fun getItemCount(): Int {
        return categoryList.size

    }



}