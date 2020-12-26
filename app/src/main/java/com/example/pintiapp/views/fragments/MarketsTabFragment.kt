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
import com.example.pintiapp.views.adapters.MarketRecyclerViewAdapter
import com.example.pintiapp.models.MarketModel
import com.example.pintiapp.R
import com.example.pintiapp.viewModels.MarketsTabViewModel

class MarketsTabFragment : Fragment(), MarketRecyclerViewAdapter.OnMarketItemClickListener {

    companion object {
        fun newInstance() = MarketsTabFragment()
    }

    private lateinit var viewModel: MarketsTabViewModel
    private lateinit var recyclerViewMarket: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.markets_tab_fragment, container, false)

        recyclerViewMarket = rootView.findViewById(R.id.recyclerViewMarket)
        recyclerViewMarket.layoutManager = GridLayoutManager(activity, 3)
        recyclerViewMarket.adapter = MarketRecyclerViewAdapter(createMarketList(), this)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarketsTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createMarketList(): List<MarketModel> {
        val market1 = MarketModel(
                "A101",
                R.drawable.im_market,
                21
        )

        val market2 = MarketModel(
                "Şok",
                R.drawable.im_market,
                21
        )

        val market3 = MarketModel(
                "BİM",
                R.drawable.im_market,
                21
        )

        val market4 = MarketModel(
                "Hakmar",
                R.drawable.im_market,
                21
        )
        val market5 = MarketModel(
                "Sakmar",
                R.drawable.im_market,
                21
        )

        return listOf(market1, market2, market4, market5)
    }

    override fun onMarketCardClick(item: MarketModel, position: Int) {
        Toast.makeText(activity, item.marketName, Toast.LENGTH_SHORT).show()
    }

}