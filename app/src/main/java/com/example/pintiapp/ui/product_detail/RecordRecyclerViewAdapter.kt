package com.example.pintiapp.ui.product_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.databinding.ItemShopCardBinding
import com.example.pintiapp.models.Record
import com.example.pintiapp.utils.ShopStatic
import com.example.pintiapp.utils.getPriceText

class RecordRecyclerViewAdapter(
    val recordList: ArrayList<Record>,
    var clickListener: OnMarketItemClickListener
) :
    RecyclerView.Adapter<RecordRecyclerViewAdapter.RecordViewHolder>() {

    class RecordViewHolder(val binding: ItemShopCardBinding) :
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
        val binding = ItemShopCardBinding.inflate(
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
