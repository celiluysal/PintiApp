package com.example.pintiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class AddProductActivity : AppCompatActivity() {
    private lateinit var textInputEditTextProductName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val barcode = intent.getStringExtra("barcode")

        textInputEditTextProductName = findViewById(R.id.textInputEditTextProductName)

        if (barcode != null) {
            textInputEditTextProductName.setText(barcode.toString())
            textInputEditTextProductName.isEnabled = false
        }

        val main_tb = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        setSupportActionBar(main_tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        main_tb.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })




    }
}