package com.example.pintiapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.Models.MarketModel
import com.example.pintiapp.R

class MarketRecyclerViewAdapter(
        val marketList: List<MarketModel>,
        var clickListener: OnMarketItemClickListener):
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
        val textViewCounter : TextView = itemView.findViewById(R.id.textViewCounter)

        fun bind(marketModel: MarketModel, action: OnMarketItemClickListener){

            imageViewContent.setImageResource(marketModel.marketPictureResource)
            textViewContentName.text = marketModel.marketName
            textViewCounter.text = marketModel.productCount.toString() + " Ürün"
            textViewCounter.textSize = 10.0F

            itemView.setOnClickListener {
                action.onMarketCardClick(marketModel, adapterPosition)
            }

        }
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
        fun onMarketCardClick(item: MarketModel, position: Int)
    }
}