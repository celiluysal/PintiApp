package com.example.pintiapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.pintiapp.R
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var zxscan: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scan)

        setToolbar()

        zxscan = findViewById(R.id.zxscan)
        zxscan.setResultHandler (this@BarcodeScanActivity)
        zxscan.startCamera()


    }

    override fun handleResult(rawResult: Result?) {

        if (rawResult != null) {
            val barcode = rawResult!!.text
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("barcode", barcode)
            startActivity(intent)
            finish()
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