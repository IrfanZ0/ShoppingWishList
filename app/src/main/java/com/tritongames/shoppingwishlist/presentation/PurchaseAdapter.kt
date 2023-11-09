package com.tritongames.shoppingwishlist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.viewmodels.ShoppingDataViewModel
import javax.inject.Inject

class PurchaseAdapter @Inject constructor(private val shopVM : ShoppingDataViewModel, private val productInfo: String) : RecyclerView.Adapter<PurchaseAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prodNameTV = itemView.findViewById<TextView>(R.id.productNameText)
        val productPriceTV = itemView.findViewById<TextView>(R.id.productPriceText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
       val view : View = LayoutInflater.from(parent.context).inflate(R.layout.purchase_layout, parent)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
       return shopVM.getProductCheckoutList(productInfo).count()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.prodNameTV.text = shopVM.getProductCheckoutList(productInfo)[position]
        holder.productPriceTV.text = shopVM.getProductCheckoutList(productInfo)[position]
    }


}