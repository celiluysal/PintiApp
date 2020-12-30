package com.example.pintiapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pintiapp.R
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.ShopStatic
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottom_nav_bar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        val navController = findNavController(R.id.main_host_fragment)
        bottom_nav_bar.setupWithNavController(navController)
    }
}