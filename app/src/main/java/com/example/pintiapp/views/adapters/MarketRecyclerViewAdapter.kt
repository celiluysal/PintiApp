package com.example.pintiapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.R
import com.example.pintiapp.models.Shop
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage

class MarketRecyclerViewAdapter(
        val marketList: ArrayList<Shop>,
        var clickListener: OnMarketItemClickListener
):
        RecyclerView.Adapter<MarketRecyclerViewAdapter.MarketViewHolder>() {

    class MarketViewHolder(container: ViewGroup): RecyclerView.ViewHolder(
            LayoutInflater.from(container.context).inflate(
                    R.layout.item_category_card,
                    container,
                    false
            )
    ){

        val imageViewContent : ImageView = itemView.findViewById(R.id.imageViewContent)
        val textViewContentName : TextView = itemView.findViewById(R.id.textViewContentName)

        fun bind(shop: Shop, action: OnMarketItemClickListener){

            imageViewContent.loadImage(shop.photoURL, getProgressDrawable(itemView.context))
            textViewContentName.text = shop.shopName

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
        return MarketViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        return holder.bind(marketList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return marketList.size
    }

    interface OnMarketItemClickListener{
        fun onMarketCardClick(item: Shop, position: Int)
    }
}