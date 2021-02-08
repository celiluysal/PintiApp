package com.example.pintiapp.ui.categories.categories_tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.databinding.ItemCategoryCardBinding
import com.example.pintiapp.models.Category
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage

class CategoryRecyclerViewAdapter(
    private val categoryList: ArrayList<Category>,
    var clickListener: OnCategoryItemClickListener
) :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: ItemCategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, action: OnCategoryItemClickListener) {

            binding.imageViewContent.loadImage(category.photoURL, getProgressDrawable(itemView.context))
            binding.textViewContentName.text = category.categoryName

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
        val binding = ItemCategoryCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        return holder.bind(categoryList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface OnCategoryItemClickListener {
        fun onCategoryCardClick(item: Category, position: Int)
    }
}