package com.example.pintiapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.pintiapp.R
import com.example.pintiapp.models.Product
import com.example.pintiapp.viewModels.BarcodeScanViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var viewModel: BarcodeScanViewModel
    private lateinit var zxscan: ZXingScannerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewNotFound: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scan)

        setToolbar()

        viewModel = ViewModelProvider(this).get(BarcodeScanViewModel::class.java)
        progressBar = findViewById(R.id.progressBar)
        textViewNotFound = findViewById(R.id.textViewNotFound)

        progressBar.visibility = ProgressBar.GONE
        textViewNotFound.visibility = TextView.GONE

        zxscan = findViewById(R.id.zxscan)
        zxscan.setResultHandler (this@BarcodeScanActivity)
        zxscan.startCamera()

    }

    override fun handleResult(rawResult: Result?) {

        if (rawResult != null) {
            val barcode = rawResult.text
            viewModel.fetchData(barcode)
            observeViewModel(barcode)


        }
    }

    private fun observeViewModel(barcode: String) {
        viewModel.products.observe(this, { products ->
            products?.let { products ->
                val intent = Intent(this, AddProductActivity::class.java)
                if (products.isNotEmpty()) {
                    intent.putExtra("product", products[0])
                    intent.putExtra("barcode", barcode)
                    startActivity(intent)
                    finish()
                } else {
                    intent.putExtra("barcode", barcode)
                    startActivity(intent)
                    finish()
                }

            }
        })

        viewModel.loading.observe(this, {
            if (it)
                progressBar.visibility = ProgressBar.VISIBLE
            else
                progressBar.visibility = ProgressBar.GONE
        })

        viewModel.loadError.observe(this, {
            if (it)
                textViewNotFound.visibility = TextView.VISIBLE
            else
                textViewNotFound.visibility = TextView.GONE
        })
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