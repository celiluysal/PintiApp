package com.example.pintiapp.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.views.adapters.CategoryRecyclerViewAdapter
import com.example.pintiapp.models.CategoryModel
import com.example.pintiapp.R
import com.example.pintiapp.viewModels.CategoriesTabViewModel

class CategoriesTabFragment : Fragment(), CategoryRecyclerViewAdapter.OnCategoryItemClickListener {

    companion object {
        fun newInstance() = CategoriesTabFragment()
    }

    private lateinit var viewModel: CategoriesTabViewModel
    private lateinit var recyclerViewCategory: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.categories_tab_fragment, container, false)

        recyclerViewCategory = rootView.findViewById(R.id.recyclerViewCategory)
        recyclerViewCategory.layoutManager = GridLayoutManager(activity, 3)
        recyclerViewCategory.adapter = CategoryRecyclerViewAdapter(createCategoryList(), this)

        return rootView
    }

    private fun createCategoryList(): List<CategoryModel> {
        val category1 = CategoryModel(
                "İçecekler",
                R.drawable.im_category,
                21
        )

        val category2 = CategoryModel(
                "İçecekler",
                R.drawable.im_category,
                21
        )

        val category3 = CategoryModel(
                "İçecekler",
                R.drawable.im_category,
                21
        )

        val category4 = CategoryModel(
                "İçecekler",
                R.drawable.im_category,
                21
        )
        val category5 = CategoryModel(
                "İçecekler",
                R.drawable.im_category,
                21
        )

        return listOf(category1, category2, category4, category3, category5)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCategoryCardClick(item: CategoryModel, position: Int) {
        Toast.makeText(activity, item.categoryName, Toast.LENGTH_SHORT).show()
    }

}