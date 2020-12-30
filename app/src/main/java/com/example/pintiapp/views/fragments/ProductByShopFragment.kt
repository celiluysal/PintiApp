package com.example.pintiapp.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pintiapp.viewModels.ProductByShopViewModel
import com.example.pintiapp.R

class ProductByShopFragment : Fragment() {

    companion object {
        fun newInstance() = ProductByShopFragment()
    }

    private lateinit var viewModel: ProductByShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_by_shop_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductByShopViewModel::class.java)
        // TODO: Use the ViewModel
    }

}