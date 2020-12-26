package com.example.pintiapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.models.ProductModel
import com.example.pintiapp.R
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage

class ProductRecyclerViewAdapter(val productList: ArrayList<ProductModel>,
                                 var clickListener: OnProductItemClickListener
):
        RecyclerView.Adapter<ProductRecyclerViewAdapter.HomeViewHolder>() {

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

        val cardViewShop1 : CardView = itemView.findViewById(R.id.cardViewShop1)
        val textViewShop1 : TextView = itemView.findViewById(R.id.textViewShop1)
        val textViewShopPrice1 : TextView = itemView.findViewById(R.id.textViewShopPrice1)

        val cardViewShop2 : CardView = itemView.findViewById(R.id.cardViewShop2)
        val textViewShop2 : TextView = itemView.findViewById(R.id.textViewShop2)
        val textViewShopPrice2 : TextView = itemView.findViewById(R.id.textViewShopPrice2)

        val cardViewShop3 : CardView = itemView.findViewById(R.id.cardViewShop3)
        val textViewShop3 : TextView = itemView.findViewById(R.id.textViewShop3)
        val textViewShopPrice3 : TextView = itemView.findViewById(R.id.textViewShopPrice3)

        fun bind(productModel: ProductModel, action: OnProductItemClickListener){
            counter_layout.visibility = RelativeLayout.INVISIBLE
            cardViewShop1.visibility = CardView.INVISIBLE
            cardViewShop2.visibility = CardView.INVISIBLE
            cardViewShop3.visibility = CardView.INVISIBLE

            imageViewProduct.loadImage(productModel.photoURL, getProgressDrawable(itemView.context))

            if (productModel.name.length > 15){
                val displayProductName = productModel.name.substring(0,15) + "..."
                textViewProductName.text = displayProductName
            }
            else
                textViewProductName.text = productModel.name

            val recordCount = productModel.recordList.size

            if (recordCount > 3){
                textViewCounter.text = (
                        recordCount.toString() + " " +
                                itemView.resources.getString(R.string.price))
                counter_layout.visibility = RelativeLayout.VISIBLE
            }
            else
                counter_layout.visibility = RelativeLayout.INVISIBLE

            if (productModel.recordList.isNotEmpty()) {
                textViewShop1.text = productModel.recordList[0].marketName
                textViewShopPrice1.text = productModel.recordList[0].productPrice + "₺"
                cardViewShop1.visibility = CardView.VISIBLE

                if (productModel.recordList.size > 1) {
                    textViewShop2.text = productModel.recordList[1].marketName
                    textViewShopPrice2.text = productModel.recordList[1].productPrice + "₺"
                    cardViewShop2.visibility = CardView.VISIBLE
                }
                else
                    cardViewShop2.visibility = CardView.INVISIBLE



                if (productModel.recordList.size > 2) {
                    textViewShop3.text = productModel.recordList[2].marketName
                    textViewShopPrice3.text = productModel.recordList[2].productPrice + "₺"
                    cardViewShop3.visibility = CardView.VISIBLE
                }
                else
                    cardViewShop3.visibility = CardView.INVISIBLE
            }
            else {
                cardViewShop1.visibility = CardView.INVISIBLE
                cardViewShop2.visibility = CardView.INVISIBLE
                cardViewShop3.visibility = CardView.INVISIBLE
            }


            itemView.setOnClickListener {
                action.onProductCardClick(productModel, adapterPosition)
            }

        }
    }

    fun updateProducts(newProducts: List<ProductModel>) {
        productList.clear()
        productList.addAll(newProducts)
        notifyDataSetChanged()
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