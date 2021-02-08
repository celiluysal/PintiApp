package com.example.pintiapp.ui.categories.categories_tab

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pintiapp.databinding.CategoriesTabFragmentBinding
import com.example.pintiapp.models.Category
import com.example.pintiapp.ui.categories.categories_tab.product_by_category.ProductByCategoryActivity
import com.example.pintiapp.base.BaseFragment

class CategoriesTabFragment : BaseFragment<CategoriesTabFragmentBinding, CategoriesTabViewModel>(),
    CategoryRecyclerViewAdapter.OnCategoryItemClickListener {

    companion object {
        fun newInstance() = CategoriesTabFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CategoriesTabFragmentBinding {
        return CategoriesTabFragmentBinding.inflate(inflater, container, false)
    }

    //    private lateinit var binding: CategoriesTabFragmentBinding
//    private lateinit var viewModel: CategoriesTabViewModel
    private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = binding.root

        binding.recyclerViewCategory.layoutManager = GridLayoutManager(activity, 3)
        categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(arrayListOf(), this)
        binding.recyclerViewCategory.adapter = categoryRecyclerViewAdapter

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesTabViewModel::class.java)

        Log.e("a","CategoriesTabFragment - onActivityCreated")

        viewModel.refresh()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categories.let { categories ->
                categoryRecyclerViewAdapter.updateCategories(categories)
            }

        })
    }

    override fun onCategoryCardClick(item: Category, position: Int) {
        Toast.makeText(activity, item.categoryName + " " + item.categoryId, Toast.LENGTH_SHORT)
            .show()
        activity?.let {
            val intent = Intent(it, ProductByCategoryActivity::class.java)
            intent.putExtra("categoryId", item.categoryId)
            it.startActivity(intent)
        }

    }


}