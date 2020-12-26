package com.example.pintiapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.pintiapp.R

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setToolbar()


    }

    private fun setToolbar(){
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)

        setSupportActionBar(toolbar)

        imageViewBack.visibility = ImageView.VISIBLE

        imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }
}