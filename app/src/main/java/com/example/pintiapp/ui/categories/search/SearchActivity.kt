package com.example.pintiapp.ui.categories.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pintiapp.base.BaseActivity
import com.example.pintiapp.databinding.ActivitySearchBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.ui.product_detail.ProductDetailActivity
import com.example.pintiapp.ui.home.ProductRecyclerViewAdapter

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(), ProductRecyclerViewAdapter.OnProductItemClickListener {

    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter

    override fun getViewBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.adView.loadAd(adRequest)

        binding.includeShowProducts.progressBarProduct.visibility = ProgressBar.GONE
        binding.includeShowProducts.textViewNotFound.visibility = TextView.GONE

        binding.includeShowProducts.recyclerviewProducts.layoutManager =
            GridLayoutManager(this, 2)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        binding.includeShowProducts.recyclerviewProducts.adapter = productRecyclerViewAdapter

        binding.includeShowProducts.swipeRefreshLayoutShowProduct.isEnabled = false

        setToolbar()


        binding.includeToolbar.searchView.onActionViewExpanded()
        binding.includeToolbar.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.refresh(newText)
                    observeViewModel()
                    return true
                } else
                    return false
            }

        })
    }

    private fun observeViewModel() {
        binding.includeShowProducts.progressBarProduct.visibility = ProgressBar.VISIBLE
        productRecyclerViewAdapter.clear()

        viewModel.products.observe(this, {
            it?.let { products ->
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
        setSupportActionBar(binding.includeToolbar.searchToolbar)
        binding.includeToolbar.imageViewBack.visibility = ImageView.VISIBLE

        setSupportActionBar(binding.includeToolbar.searchToolbar)

        binding.includeToolbar.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }




}