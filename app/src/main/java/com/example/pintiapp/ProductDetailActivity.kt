package com.example.pintiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.Adapters.RecordRecyclerViewAdapter
import com.example.pintiapp.Models.ProductModel
import com.example.pintiapp.ViewModels.HomePageViewModel

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var recyclerviewRecords: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setToolbar()

        val product = intent.extras?.get("product") as ProductModel

        val textViewProductName = findViewById<TextView>(R.id.textViewProductName)
        val imageViewProduct : ImageView = findViewById(R.id.imageViewProduct)

        textViewProductName.text = product.productName
        imageViewProduct.setImageResource(product.productPictureResource)

        recyclerviewRecords = findViewById(R.id.recyclerviewRecords)
        recyclerviewRecords.layoutManager = LinearLayoutManager(this)
        recyclerviewRecords.adapter = RecordRecyclerViewAdapter(product.recordList)

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