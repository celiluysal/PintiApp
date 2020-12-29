package com.example.pintiapp.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.views.adapters.CategoryRecyclerViewAdapter
import com.example.pintiapp.models.CategoryModel
import com.example.pintiapp.R
import com.example.pintiapp.models.Category
import com.example.pintiapp.viewModels.CategoriesTabViewModel

class CategoriesTabFragment : Fragment(), CategoryRecyclerViewAdapter.OnCategoryItemClickListener {

    companion object {
        fun newInstance() = CategoriesTabFragment()
    }

    private lateinit var viewModel: CategoriesTabViewModel
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.categories_tab_fragment, container, false)

        recyclerViewCategory = rootView.findViewById(R.id.recyclerViewCategory)
        recyclerViewCategory.layoutManager = GridLayoutManager(activity, 3)
        categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(arrayListOf(),this)
        recyclerViewCategory.adapter = categoryRecyclerViewAdapter

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesTabViewModel::class.java)
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
        Toast.makeText(activity,item.categoryName,Toast.LENGTH_SHORT).show()
    }

}