package com.example.pintiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.pintiapp.Fragments.CategoriesPageFragment
import com.example.pintiapp.Fragments.HomePageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
//    private lateinit var bottom_nav_bar : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottom_nav_bar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)

        val navController = findNavController(R.id.main_frame_layout)

        bottom_nav_bar.setupWithNavController(navController)



    }


}