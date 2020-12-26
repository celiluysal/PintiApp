package com.example.pintiapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.models.CategoryModel
import com.example.pintiapp.R

class CategoryRecyclerViewAdapter(
        val categoryList: List<CategoryModel>,
        var clickListener: OnCategoryItemClickListener
):
        RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(container: ViewGroup): RecyclerView.ViewHolder(
            LayoutInflater.from(container.context).inflate(
                    R.layout.item_category_card,
                    container,
                    false
            )
    ){

        val imageViewContent : ImageView = itemView.findViewById(R.id.imageViewContent)
        val textViewContentName : TextView = itemView.findViewById(R.id.textViewContentName)
        val textViewCounter : TextView = itemView.findViewById(R.id.textViewCounter)

        fun bind(categoryModel: CategoryModel, action: OnCategoryItemClickListener){

            imageViewContent.setImageResource(categoryModel.categoryPictureResource)
            textViewContentName.text = categoryModel.categoryName
            textViewCounter.text = categoryModel.productCount.toString() + " Ürün"
            textViewCounter.textSize = 10.0F

            itemView.setOnClickListener {
                action.onCategoryCardClick(categoryModel, adapterPosition)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        return holder.bind(categoryList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface OnCategoryItemClickListener{
        fun onCategoryCardClick(item: CategoryModel, position: Int)
    }
        }