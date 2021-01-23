package com.example.pintiapp.views.adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ItemMarketCardBinding
import com.example.pintiapp.models.Record
import com.example.pintiapp.utils.ShopStatic
import com.example.pintiapp.utils.getPriceText
import java.math.RoundingMode
import java.text.DecimalFormat

class RecordRecyclerViewAdapter(
    val recordList: ArrayList<Record>,
    var clickListener: OnMarketItemClickListener
) :
    RecyclerView.Adapter<RecordRecyclerViewAdapter.RecordViewHolder>() {

    class RecordViewHolder(val binding: ItemMarketCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: Record, action: OnMarketItemClickListener) {
            binding.textViewMarketName.text = ShopStatic.shared.getShopName(record.shopId)
            binding.textViewLocationTitle.text = record.locationTitle
            binding.textViewRecordDate.text = record.recordDate
            binding.textViewRecordOwner.text = record.ownerName
            binding.textViewPrice.text = getPriceText(itemView.context, record.price)

            binding.imageViewLocation.setOnClickListener {
                action.onMarketCardClick(record, adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ItemMarketCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        return holder.bind(recordList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    interface OnMarketItemClickListener {
        fun onMarketCardClick(item: Record, position: Int)
    }

}
