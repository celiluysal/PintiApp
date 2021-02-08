package com.example.pintiapp.ui.categories.shops_tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.databinding.ItemCategoryCardBinding
import com.example.pintiapp.models.Shop
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage

class ShopRecyclerViewAdapter(
    val marketList: ArrayList<Shop>,
    var clickListener: OnMarketItemClickListener
) :
    RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopViewHolder>() {

    class ShopViewHolder(val binding: ItemCategoryCardBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemCategoryCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        return holder.bind(marketList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return marketList.size
    }

    interface OnMarketItemClickListener {
        fun onMarketCardClick(item: Shop, position: Int)
    }
}