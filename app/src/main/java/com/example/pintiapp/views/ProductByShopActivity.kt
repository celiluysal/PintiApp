package com.example.pintiapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pintiapp.R
import com.example.pintiapp.models.Product
import com.example.pintiapp.viewModels.ProductByShopViewModel
import com.example.pintiapp.views.adapters.ProductRecyclerViewAdapter

class ProductByShopActivity : AppCompatActivity(), ProductRecyclerViewAdapter.OnProductItemClickListener {
    private lateinit var viewModel: ProductByShopViewModel
    private lateinit var swipeRefreshLayoutHomeFragment: SwipeRefreshLayout
    private lateinit var recyclerviewProducts: RecyclerView
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter
    private lateinit var progressBarProduct: ProgressBar
    private lateinit var textViewNotFound: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_by_shop)

        val shopId: String = intent.getStringExtra("shopId").toString()

        setToolbar()


        viewModel = ViewModelProvider(this).get(ProductByShopViewModel::class.java)
        viewModel.refresh(shopId)

        swipeRefreshLayoutHomeFragment = findViewById(R.id.swipeRefreshLayoutShowProduct)
        recyclerviewProducts = findViewById(R.id.recyclerviewProducts)
        progressBarProduct = findViewById(R.id.progressBarProduct)
        textViewNotFound = findViewById(R.id.textViewNotFound)

        progressBarProduct.visibility = ProgressBar.VISIBLE
        textViewNotFound.visibility = TextView.GONE

        recyclerviewProducts.layoutManager = GridLayoutManager(this, 2)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        recyclerviewProducts.adapter = productRecyclerViewAdapter

        swipeRefreshLayoutHomeFragment.setOnRefreshListener {
            swipeRefreshLayoutHomeFragment.isRefreshing = false
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
                progressBarProduct.visibility = ProgressBar.VISIBLE
            else
                progressBarProduct.visibility = ProgressBar.GONE
        })

        viewModel.loadError.observe(this, {
            if (it)
                textViewNotFound.visibility = TextView.VISIBLE
            else
                textViewNotFound.visibility = TextView.GONE

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

    private fun setToolbar(){
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)

        setSupportActionBar(toolbar)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.VISIBLE

        imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }
}