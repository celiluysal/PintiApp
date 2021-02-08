package com.example.pintiapp.ui.categories.categories_tab.product_by_category

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
import com.example.pintiapp.databinding.ActivityProductByCategoryBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.ui.product_detail.ProductDetailActivity
import com.example.pintiapp.ui.home.ProductRecyclerViewAdapter

class ProductByCategoryActivity :
    BaseActivity<ActivityProductByCategoryBinding, ProductByCategoryViewModel>(),
    ProductRecyclerViewAdapter.OnProductItemClickListener {

    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter

    override fun getViewBinding(): ActivityProductByCategoryBinding {
        return ActivityProductByCategoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar()

        binding.adView.loadAd(adRequest)

        val categoryId: String = intent.getStringExtra("categoryId").toString()

        viewModel = ViewModelProvider(this).get(ProductByCategoryViewModel::class.java)
        viewModel.refresh(categoryId)

        binding.includeShowProducts.progressBarProduct.visibility = ProgressBar.VISIBLE
        binding.includeShowProducts.textViewNotFound.visibility = TextView.GONE

        binding.includeShowProducts.recyclerviewProducts.layoutManager = GridLayoutManager(this, 2)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        binding.includeShowProducts.recyclerviewProducts.adapter = productRecyclerViewAdapter

        binding.includeShowProducts.swipeRefreshLayoutShowProduct.setOnRefreshListener {
            binding.includeShowProducts.swipeRefreshLayoutShowProduct.isRefreshing = false
            viewModel.refresh(categoryId)
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