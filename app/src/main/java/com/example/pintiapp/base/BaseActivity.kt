package com.example.pintiapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

abstract class BaseActivity<VB: ViewBinding, VM: ViewModel>: AppCompatActivity() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM
    protected val adRequest: AdRequest = AdRequest.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        MobileAds.initialize(this) {}
    }

    protected abstract fun getViewBinding(): VB
}