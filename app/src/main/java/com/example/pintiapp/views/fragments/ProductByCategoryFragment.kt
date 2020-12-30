package com.example.pintiapp.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pintiapp.R
import com.example.pintiapp.viewModels.ProductByCategoryViewModel

class ProductByCategoryFragment : Fragment() {

    companion object {
        fun newInstance() = ProductByCategoryFragment()
    }

    private lateinit var viewModel: ProductByCategoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.product_by_category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductByCategoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}