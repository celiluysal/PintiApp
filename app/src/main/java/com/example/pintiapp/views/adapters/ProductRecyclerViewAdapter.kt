package com.example.pintiapp.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ItemProductCardBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.utils.*

class ProductRecyclerViewAdapter(
    val productList: ArrayList<Product>,
    var clickListener: OnProductItemClickListener
) :
    RecyclerView.Adapter<ProductRecyclerViewAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: ItemProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, action: OnProductItemClickListener) {
            binding.counterLayout.visibility = RelativeLayout.INVISIBLE
            binding.cardViewShop1.visibility = CardView.INVISIBLE
            binding.cardViewShop2.visibility = CardView.INVISIBLE
            binding.cardViewShop3.visibility = CardView.INVISIBLE

            binding.imageViewProduct.loadImage(product.photoURL, getProgressDrawable(itemView.context))

            binding.textViewProductName.text = product.name

            val recordCount = product.records.size
            if (recordCount > 3) {
                binding.includeCounterCard.textViewCounter.text = (
                        recordCount.toString() + " " +
                                itemView.resources.getString(R.string.price))
                binding.counterLayout.visibility = RelativeLayout.VISIBLE
            } else
                binding.counterLayout.visibility = RelativeLayout.INVISIBLE
//
            if (product.records.isNotEmpty()) {
                binding.textViewShop1.text = ShopStatic.shared.getShopName(product.records[0].shopId)
                binding.textViewShopPrice1.text = getPriceText(itemView.context, product.records[0].price)
                binding.cardViewShop1.visibility = CardView.VISIBLE

                if (product.records.size > 1) {
                    binding.textViewShop2.text = ShopStatic.shared.getShopName(product.records[1].shopId)
                    binding.textViewShopPrice2.text =
                        getPriceText(itemView.context, product.records[1].price)
                    binding.cardViewShop2.visibility = CardView.VISIBLE
                } else
                    binding.cardViewShop2.visibility = CardView.INVISIBLE


                if (product.records.size > 2) {
                    binding.textViewShop3.text = ShopStatic.shared.getShopName(product.records[2].shopId)
                    binding.textViewShopPrice3.text =
                        getPriceText(itemView.context, product.records[2].price)
                    binding.cardViewShop3.visibility = CardView.VISIBLE
                } else
                    binding.cardViewShop3.visibility = CardView.INVISIBLE
            } else {
                binding.cardViewShop1.visibility = CardView.INVISIBLE
                binding.cardViewShop2.visibility = CardView.INVISIBLE
                binding.cardViewShop3.visibility = CardView.INVISIBLE
            }

            itemView.setOnClickListener {
                action.onProductCardClick(product, adapterPosition)
            }

        }
    }

    fun updateProducts(newProducts: List<Product>) {
        productList.clear()
        productList.addAll(newProducts)
        notifyDataSetChanged()
    }

    fun clear() {
        productList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemProductCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        return holder.bind(productList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface OnProductItemClickListener {
        fun onProductCardClick(item: Product, position: Int)
    }


}