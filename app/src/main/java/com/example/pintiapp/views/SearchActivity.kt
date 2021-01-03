package com.example.pintiapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.R
import com.example.pintiapp.models.Product
import com.example.pintiapp.viewModels.SearchViewModel
import com.example.pintiapp.views.adapters.ProductRecyclerViewAdapter

class SearchActivity : AppCompatActivity(), ProductRecyclerViewAdapter.OnProductItemClickListener {

    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var viewModel: SearchViewModel
    private lateinit var recyclerviewProducts: RecyclerView
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter
    private lateinit var progressBarProduct: ProgressBar
    private lateinit var textViewNotFound: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)


        recyclerviewProducts = findViewById(R.id.recyclerviewProducts)
        progressBarProduct = findViewById(R.id.progressBarProduct)
        textViewNotFound = findViewById(R.id.textViewNotFound)
        searchView = findViewById(R.id.searchView)

        progressBarProduct.visibility = ProgressBar.GONE
        textViewNotFound.visibility = TextView.GONE

        recyclerviewProducts.layoutManager = GridLayoutManager(this, 2)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        recyclerviewProducts.adapter = productRecyclerViewAdapter


        setToolbar()

        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
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
        progressBarProduct.visibility = ProgressBar.VISIBLE
        productRecyclerViewAdapter.clear()

        viewModel.products.observe(this, {
            it?.let { products ->
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

    private fun setToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)

        setSupportActionBar(toolbar)

        imageViewBack.visibility = ImageView.VISIBLE

        imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }


}