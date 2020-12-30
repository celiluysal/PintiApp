package com.example.pintiapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.views.adapters.RecordRecyclerViewAdapter
import com.example.pintiapp.R
import com.example.pintiapp.models.Product
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.utils.loadImage

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var recyclerviewRecords: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setToolbar()

        val product = intent.extras?.get("product") as Product

        val imageViewProduct: ImageView = findViewById(R.id.imageViewProduct)
        val textViewProductName: TextView = findViewById(R.id.textViewProductName)
        val textViewProductBrandAndCategory: TextView = findViewById(R.id.textViewProductBrandAndCategory)


        imageViewProduct.loadImage(product.photoURL, getProgressDrawable(this))
        textViewProductName.text = product.name
        val brand = product.brand
        val category = CategoryStatic.shared.getCategoryName(product.categoryId)
        val text = "$brand - $category"
        textViewProductBrandAndCategory.text = text

        recyclerviewRecords = findViewById(R.id.recyclerviewRecords)
        recyclerviewRecords.layoutManager = LinearLayoutManager(this)
        recyclerviewRecords.adapter = RecordRecyclerViewAdapter(product.records)

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