package com.example.pintiapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.core.view.size
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ActivityMainBinding
import com.example.pintiapp.base.BaseActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds

class MainActivity: BaseActivity<ActivityMainBinding, ViewModel>() {

//    private lateinit var binding: ActivityMainBinding

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("a","MainActivity, onCreate")

        val navController = findNavController(R.id.main_host_fragment)
        binding.bottomNavBar.setupWithNavController(navController)

        binding.adView.loadAd(adRequest)
    }





}