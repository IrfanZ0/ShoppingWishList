package com.tritongames.shopwishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shopwishlist.JSONclasses.wishes
import com.tritongames.shopwishlist.data.repository.WishesResponse
import com.tritongames.shopwishlist.databinding.ItemWishBindingBinding

class WishesAdapter: RecyclerView.Adapter<WishesAdapter.WishesViewHolder>(){
    class WishesViewHolder(var binding: ItemWishBindingBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object: DiffUtil.ItemCallback<WishesResponse>(){
        override fun areItemsTheSame(oldItem: WishesResponse, newItem: WishesResponse): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(oldItem: WishesResponse, newItem: WishesResponse): Boolean {
           return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var wishesList: List<WishesResponse>
    get() = differ.currentList
    set(value) {differ.submitList(value)}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishesViewHolder {
        return WishesViewHolder(ItemWishBindingBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: WishesViewHolder, position: Int) {
        holder.binding.apply{
            val wish = wishes[position]


        }
    }

    override fun getItemCount(): Int {
        return wishesList.size
    }
}

