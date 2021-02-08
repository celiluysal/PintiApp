package com.example.pintiapp.ui.categories.shops_tab.product_by_shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pintiapp.base.BaseActivity
import com.example.pintiapp.databinding.ActivityProductByShopBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.ui.product_detail.ProductDetailActivity
import com.example.pintiapp.ui.home.ProductRecyclerViewAdapter

class ProductByShopActivity : BaseActivity<ActivityProductByShopBinding, ProductByShopViewModel>(),
    ProductRecyclerViewAdapter.OnProductItemClickListener {
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter

    override fun getViewBinding(): ActivityProductByShopBinding {
        return ActivityProductByShopBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shopId: String = intent.getStringExtra("shopId").toString()

        setToolbar()

        binding.adView.loadAd(adRequest)

        viewModel = ViewModelProvider(this).get(ProductByShopViewModel::class.java)
        viewModel.refresh(shopId)


        binding.includeShowProducts

        binding.includeShowProducts.progressBarProduct.visibility = ProgressBar.VISIBLE
        binding.includeShowProducts.textViewNotFound.visibility = TextView.GONE

        binding.includeShowProducts.recyclerviewProducts.layoutManager = GridLayoutManager(this, 2)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        binding.includeShowProducts.recyclerviewProducts.adapter = productRecyclerViewAdapter

        binding.includeShowProducts.swipeRefreshLayoutShowProduct.setOnRefreshListener {
            binding.includeShowProducts.swipeRefreshLayoutShowProduct.isRefreshing = false
            viewModel.refresh(shopId)
            observeViewModel()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.products.observe(this, { products ->
            products?.let { products ->
                productRecyclerViewAdapter.updateProducts(products)
            }
        })

        viewModel.loading.observe(this, {
            if (it)
                binding.includeShowProducts.progressBarProduct.visibility = ProgressBar.VISIBLE
            else
                binding.includeShowProducts.progressBarProduct.visibility = ProgressBar.GONE
        })

        viewModel.loadError.observe(this, {
            if (it)
                binding.includeShowProducts.textViewNotFound.visibility = TextView.VISIBLE
            else
                binding.includeShowProducts.textViewNotFound.visibility = TextView.GONE

        })
    }

    override fun onProductCardClick(item: Product, position: Int) {
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
        this.let {
            val intent = Intent(it, ProductDetailActivity::class.java)
            intent.putExtra("product", item)
            it.startActivity(intent)
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.includeToolbar.mainToolbar)
        binding.includeToolbar.imageViewSearch.visibility = ImageView.INVISIBLE
        binding.includeToolbar.imageViewBack.visibility = ImageView.VISIBLE

        setSupportActionBar(binding.includeToolbar.mainToolbar)

        binding.includeToolbar.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }


}