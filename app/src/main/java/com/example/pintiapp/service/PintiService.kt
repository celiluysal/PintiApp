package com.example.pintiapp.service

import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Products
import io.reactivex.Single
import javax.inject.Inject

class PintiService {

    @Inject
    lateinit var api: PintiApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getProducts(): Single<Products> {
        return api.fetchLastProducts()
    }
}