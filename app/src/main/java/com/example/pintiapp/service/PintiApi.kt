package com.example.pintiapp.service

import com.example.pintiapp.models.Category
import com.example.pintiapp.models.Product
import com.example.pintiapp.models.Shop
import io.reactivex.Single
import retrofit2.http.GET

interface PintiApi {

    @GET("fetch-last-products")
    fun fetchLastProducts(): Single<List<Product>>

    @GET("fetch-shops")
    fun fetchShops(): Single<List<Shop>>

    @GET("fetch-categories")
    fun fetchCategories(): Single<List<Category>>
}