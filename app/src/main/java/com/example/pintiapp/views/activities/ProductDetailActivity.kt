package com.example.pintiapp.views.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.views.adapters.RecordRecyclerViewAdapter
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ActivityProductDetailBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.models.Record
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage
import com.example.pintiapp.views.adapters.MarketRecyclerViewAdapter
import com.example.pintiapp.views.adapters.ProductRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductDetailActivity : AppCompatActivity(),
    RecordRecyclerViewAdapter.OnMarketItemClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var recordRecyclerViewAdapter : RecordRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        val product = intent.extras?.get("product") as Product

        binding.imageViewProduct.loadImage(product.photoURL, getProgressDrawable(this))
        binding.textViewProductName.text = product.name
        val brand = product.brand
        val category = CategoryStatic.shared.getCategoryName(product.categoryId)
        val text = "$brand - $category"
        binding.textViewProductBrandAndCategory.text = text

        binding.recyclerviewRecords.layoutManager = LinearLayoutManager(this)
        recordRecyclerViewAdapter = RecordRecyclerViewAdapter(ArrayList(product.records), this)
        binding.recyclerviewRecords.adapter = recordRecyclerViewAdapter
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

    override fun onMarketCardClick(item: Record, position: Int) {
        Log.e("adf","ProductDetailActivity onMarketCardClick")

        // Map point based on address
        val mapIntent: Intent = Uri.parse(
            "https://www.google.com/maps/search/?api=1&query="+item.locationCoordinate
        ).let { location ->
            // Or map point based on latitude/longitude
            // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
            Intent(Intent.ACTION_VIEW, location)
        }

        startActivity(mapIntent)
    }
}