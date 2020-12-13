package com.example.pintiapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var zxscan: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scan)

        zxscan = findViewById(R.id.zxscan)
        zxscan.setResultHandler (this@BarcodeScanActivity)
        zxscan.startCamera()

        val main_tb = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        setSupportActionBar(main_tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        main_tb.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })

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
}