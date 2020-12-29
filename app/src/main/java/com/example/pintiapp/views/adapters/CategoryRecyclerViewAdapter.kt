package com.example.pintiapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.R
import com.example.pintiapp.models.Category
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage

class CategoryRecyclerViewAdapter(
        val categoryList: ArrayList<Category>,
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

        fun bind(category: Category, action: OnCategoryItemClickListener){

            imageViewContent.loadImage(category.photoURL, getProgressDrawable(itemView.context))
            textViewContentName.text = category.categoryName

            itemView.setOnClickListener {
                action.onCategoryCardClick(category, adapterPosition)
            }

        }
    }

    fun updateCategories(newCategories: List<Category>) {
        categoryList.clear()
        categoryList.addAll(newCategories)
        notifyDataSetChanged()
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
        fun onCategoryCardClick(item: Category, position: Int)
    }
        }