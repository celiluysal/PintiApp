package com.example.pintiapp.ui.home

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pintiapp.ui.product_detail.ProductDetailActivity
import com.example.pintiapp.R
import com.example.pintiapp.databinding.HomePageFragmentBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.ShopStatic
import com.example.pintiapp.base.BaseFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class HomePageFragment :
    BaseFragment<HomePageFragmentBinding, HomePageViewModel>(),
    ProductRecyclerViewAdapter.OnProductItemClickListener {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomePageFragmentBinding {
        return HomePageFragmentBinding.inflate(inflater, container, false)
    }

    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        setToolbar()

//        val adRequest = AdRequest.Builder().build()
//        activity?.let {
//            InterstitialAd.load(
//                it,
//                "ca-app-pub-3940256099942544/1033173712",
//                adRequest,
//                object : InterstitialAdLoadCallback() {
//                    override fun onAdFailedToLoad(adError: LoadAdError) {
//                        Log.e("TAG", adError.message);
//                    }
//
//                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                        Log.e("TAG", "Ad was loaded.");
//                        mInterstitialAd = interstitialAd
//                    }
//                })
//        }

        binding.includeLayout.progressBarProduct.visibility = ProgressBar.VISIBLE
        binding.includeLayout.textViewNotFound.visibility = TextView.GONE

        binding.includeLayout.recyclerviewProducts.layoutManager = GridLayoutManager(activity, 2)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        binding.includeLayout.recyclerviewProducts.adapter = productRecyclerViewAdapter

        binding.includeLayout.swipeRefreshLayoutShowProduct.setOnRefreshListener {
            binding.includeLayout.swipeRefreshLayoutShowProduct.isRefreshing = false
            viewModel.refresh()
            observeViewModel()
        }

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(context as Activity)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        viewModel.refresh()

        ShopStatic.shared.refresh()
        CategoryStatic.shared.refresh()

        ShopStatic.shared.shops.observe(viewLifecycleOwner, Observer {
            observeViewModel()
        })
    }


    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let { products ->
                productRecyclerViewAdapter.updateProducts(products)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.includeLayout.progressBarProduct.visibility = ProgressBar.VISIBLE
            else
                binding.includeLayout.progressBarProduct.visibility = ProgressBar.GONE
        })

        viewModel.loadError.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.includeLayout.textViewNotFound.visibility = TextView.VISIBLE
            else
                binding.includeLayout.textViewNotFound.visibility = TextView.GONE
        })
    }


    private fun setToolbar() {
        val m = (activity as AppCompatActivity)
        val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE
    }


    override fun onProductCardClick(item: Product, position: Int) {

        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra("product", item)
        startActivity(intent)


    }
}


