package com.example.pintiapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.Models.ProductModel
import com.example.pintiapp.R

class ProductRecyclerViewAdapter(val productList: List<ProductModel>, var clickListener: OnProductItemClickListener): RecyclerView.Adapter<ProductRecyclerViewAdapter.HomeViewHolder>() {

    class HomeViewHolder(container: ViewGroup): RecyclerView.ViewHolder(
            LayoutInflater.from(container.context).inflate(
                    R.layout.item_product_card,
                    container,
                    false
            )
    ) {
        val imageViewProduct : ImageView = itemView.findViewById(R.id.imageViewProduct)
        val textViewProductName : TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewCounter : TextView = itemView.findViewById(R.id.textViewCounter)
        val counter_layout : RelativeLayout = itemView.findViewById(R.id.counter_layout)

//        fun init(item: ProductModel, action: OnProductItemClickListener) {
//
//        }

        fun bind(productModel: ProductModel, action: OnProductItemClickListener){
            imageViewProduct.setImageResource(productModel.productPictureResource)

            if (productModel.productName.length > 15){
                val displayProductName = productModel.productName.substring(0,15) + "..."
                textViewProductName.text = displayProductName
            }
            else
                textViewProductName.text = productModel.productName

            if (productModel.recordCount > 3){
                textViewCounter.text = (
                        productModel.recordCount.toString() + " " +
                                itemView.resources.getString(R.string.price))
            }
            else
                counter_layout.visibility = RelativeLayout.INVISIBLE

            itemView.setOnClickListener {
                action.onProductCardClick(productModel, adapterPosition)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        return HomeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        return holder.bind(productList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface OnProductItemClickListener{
        fun onProductCardClick(item: ProductModel, position: Int)
    }


}