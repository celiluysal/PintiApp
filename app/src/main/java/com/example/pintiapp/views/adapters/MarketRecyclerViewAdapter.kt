package com.example.pintiapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ItemCategoryCardBinding
import com.example.pintiapp.databinding.ItemMarketCardBinding
import com.example.pintiapp.models.Shop
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage

class MarketRecyclerViewAdapter(
    val marketList: ArrayList<Shop>,
    var clickListener: OnMarketItemClickListener
) :
    RecyclerView.Adapter<MarketRecyclerViewAdapter.MarketViewHolder>() {

    class MarketViewHolder(val binding: ItemCategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shop: Shop, action: OnMarketItemClickListener) {



            binding.imageViewContent.loadImage(shop.photoURL, getProgressDrawable(itemView.context))
            binding.textViewContentName.text = shop.shopName

            itemView.setOnClickListener {
                action.onMarketCardClick(shop, adapterPosition)
            }

        }
    }

    fun updateShops(newShops: List<Shop>) {
        marketList.clear()
        marketList.addAll(newShops)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val binding = ItemCategoryCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MarketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        return holder.bind(marketList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return marketList.size
    }

    interface OnMarketItemClickListener {
        fun onMarketCardClick(item: Shop, position: Int)
    }
}