package com.example.pintiapp.views.fragments

import android.content.Intent
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
import com.example.pintiapp.views.adapters.MarketRecyclerViewAdapter
import com.example.pintiapp.models.MarketModel
import com.example.pintiapp.R
import com.example.pintiapp.models.Shop
import com.example.pintiapp.viewModels.MarketsTabViewModel
import com.example.pintiapp.views.ProductByShopActivity

class MarketsTabFragment : Fragment(), MarketRecyclerViewAdapter.OnMarketItemClickListener {

    companion object {
        fun newInstance() = MarketsTabFragment()
    }

    private lateinit var viewModel: MarketsTabViewModel
    private lateinit var recyclerViewMarket: RecyclerView
    private lateinit var marketRecyclerViewAdapter: MarketRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.markets_tab_fragment, container, false)

        recyclerViewMarket = rootView.findViewById(R.id.recyclerViewMarket)
        recyclerViewMarket.layoutManager = GridLayoutManager(activity, 3)
        marketRecyclerViewAdapter = MarketRecyclerViewAdapter(arrayListOf(),this)
        recyclerViewMarket.adapter = marketRecyclerViewAdapter
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarketsTabViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.shops.observe(viewLifecycleOwner, Observer { shops ->
            shops.let { shops ->
                marketRecyclerViewAdapter.updateShops(shops)
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