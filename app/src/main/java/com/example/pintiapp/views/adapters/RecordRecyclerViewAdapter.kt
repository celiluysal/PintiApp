package com.example.pintiapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.models.RecordModel
import com.example.pintiapp.R

class RecordRecyclerViewAdapter (val recordList: List<RecordModel>): RecyclerView.Adapter<RecordRecyclerViewAdapter.RecordViewHolder>() {

    class RecordViewHolder(container: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(container.context).inflate(
                    R.layout.item_market_card,
                    container,
                    false
            )
    ) {
        val imageViewLocation: ImageView = itemView.findViewById(R.id.imageViewLocation)
        val textViewMarketName: TextView = itemView.findViewById(R.id.textViewMarketName)
        val textViewLocationTitle: TextView = itemView.findViewById(R.id.textViewLocationTitle)
        val textViewRecordDate: TextView = itemView.findViewById(R.id.textViewRecordDate)
        val textViewRecordOwner: TextView = itemView.findViewById(R.id.textViewRecordOwner)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)

//

        fun bind(recordModel: RecordModel) {
            textViewMarketName.text = recordModel.marketName
            textViewLocationTitle.text = recordModel.locationTitle
            textViewRecordDate.text = recordModel.recordDate
            textViewRecordOwner.text = recordModel.recordOwner
            textViewPrice.text = recordModel.productPrice + "₺"

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {

        return RecordViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        return holder.bind(recordList[position])
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    interface OnMarketItemClickListener {
        fun onMarketCardClick(item: RecordModel, position: Int)
    }

}
