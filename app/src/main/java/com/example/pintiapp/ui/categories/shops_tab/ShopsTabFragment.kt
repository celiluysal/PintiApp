package com.example.pintiapp.ui.categories.shops_tab

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pintiapp.databinding.ShopsTabFragmentBinding
import com.example.pintiapp.models.Shop
import com.example.pintiapp.ui.categories.shops_tab.product_by_shop.ProductByShopActivity
import com.example.pintiapp.base.BaseFragment

class ShopsTabFragment : BaseFragment<ShopsTabFragmentBinding, ShopsTabViewModel>(), ShopRecyclerViewAdapter.OnMarketItemClickListener {

    companion object {
        fun newInstance() = ShopsTabFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ShopsTabFragmentBinding {
        return ShopsTabFragmentBinding.inflate(inflater, container, false)
    }

    private lateinit var shopRecyclerViewAdapter: ShopRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = binding.root

        binding.recyclerViewMarket.layoutManager = GridLayoutManager(activity, 3)
        shopRecyclerViewAdapter = ShopRecyclerViewAdapter(arrayListOf(),this)
        binding.recyclerViewMarket.adapter = shopRecyclerViewAdapter
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopsTabViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.shops.observe(viewLifecycleOwner, Observer { shops ->
            shops.let { shops ->
                shopRecyclerViewAdapter.updateShops(shops)
            }
        })
    }


    override fun onMarketCardClick(item: Shop, position: Int) {
        Toast.makeText(activity, item.shopName + " " + item.shopId, Toast.LENGTH_SHORT).show()
        activity?.let {
            val intent = Intent(it, ProductByShopActivity::class.java)
            intent.putExtra("shopId", item.shopId)
            it.startActivity(intent)
        }
    }



}