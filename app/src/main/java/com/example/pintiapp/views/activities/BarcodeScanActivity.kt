package com.example.pintiapp.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ActivityBarcodeScanBinding
import com.example.pintiapp.viewModels.BarcodeScanViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var viewModel: BarcodeScanViewModel
    private lateinit var binding: ActivityBarcodeScanBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarcodeScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        viewModel = ViewModelProvider(this).get(BarcodeScanViewModel::class.java)

        binding.progressBar.visibility = ProgressBar.GONE
        binding.textViewNotFound.visibility = TextView.GONE

        binding.zxScanView.setResultHandler (this@BarcodeScanActivity)
        binding.zxScanView.startCamera()

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
                binding.progressBar.visibility = ProgressBar.VISIBLE
            else
                binding.progressBar.visibility = ProgressBar.GONE
        })

        viewModel.loadError.observe(this, {
            if (it)
                binding.textViewNotFound.visibility = TextView.VISIBLE
            else
                binding.textViewNotFound.visibility = TextView.GONE
        })
    }


    private fun setToolbar(){
        setSupportActionBar(binding.includeToolbar.mainToolbar)
        binding.includeToolbar.imageViewSearch.visibility = ImageView.INVISIBLE
        binding.includeToolbar.imageViewBack.visibility = ImageView.VISIBLE

        binding.includeToolbar.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }
}