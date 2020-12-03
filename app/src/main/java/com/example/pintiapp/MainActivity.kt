package com.example.pintiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.FrameLayout
import android.widget.Toolbar
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

//        val tb1 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_1)
//        val tb2 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_2)
//
//        tb1.visibility = Toolbar.VISIBLE
//        tb2.visibility = Toolbar.INVISIBLE
//
//        tb2.visibility = Toolbar.VISIBLE
//        tb1.visibility = Toolbar.INVISIBLE



//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        val tb = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val tb2 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_2)
//        setSupportActionBar(tb)
        setSupportActionBar(tb2)
//        supportActionBar?.title = ""

//        supportActionBar?.title = "dsg"

//        setSupportActionBar(toolbar)
        val bottom_nav_bar = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)

        val navController = findNavController(R.id.main_frame_layout)

        bottom_nav_bar.setupWithNavController(navController)

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

}