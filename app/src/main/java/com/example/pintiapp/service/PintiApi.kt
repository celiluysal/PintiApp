package com.example.pintiapp.service

import com.example.pintiapp.models.Product
import com.example.pintiapp.models.ProductModel
import com.example.pintiapp.models.Products
import io.reactivex.Single
import retrofit2.http.GET

interface PintiApi {

    @GET("fetch-last-products")
    fun fetchLastProducts(): Single<Products>

}