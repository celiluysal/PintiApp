package com.example.pintiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottom_nav_bar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        val navController = findNavController(R.id.main_host_fregment)
        bottom_nav_bar.setupWithNavController(navController)


    }



}