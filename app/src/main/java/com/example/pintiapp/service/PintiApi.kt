package com.example.pintiapp.service

import com.example.pintiapp.models.Category
import com.example.pintiapp.models.Product
import com.example.pintiapp.models.Result
import com.example.pintiapp.models.Shop
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PintiApi {

    @GET("fetch-last-products")
    fun fetchLastProducts(): Single<List<Product>>

    @GET("fetch-shops")
    fun fetchShops(): Single<List<Shop>>

    @GET("fetch-categories")
    fun fetchCategories(): Single<List<Category>>

    @GET("fetch-products-by-shop")
    fun fetchProductsByShopId(
        @Query("shopid")
        shopId: String
    ): Single<List<Product>>

    @GET("fetch-products-by-category")
    fun fetchProductsByCategoryId(
        @Query("categoryid")
        categoryId: String
    ): Single<List<Product>>

    @GET("find-product")
    fun findProduct(
        @Query("barcode")
        barcode: String
    ): Single<List<Product>>

    @GET("add-product")
    fun addProduct(
        @Query("barcode")
        barcode: String,
        @Query("brand")
        brand: String,
        @Query("categoryid")
        categoryid: String,
        @Query("photourl")
        photourl: String,
        @Query("name")
        name: String
    ): Single<Result>

    @GET("add-record")
    fun addRecord(
        @Query("barcode")
        barcode: String,
        @Query("ownerid")
        ownerid: String,
        @Query("ownername")
        ownername: String,
        @Query("shopid")
        shopid: String,
        @Query("locationtitle")
        locationtitle: String,
        @Query("locationcoordinate")
        locationcoordinate: String,
        @Query("price")
        price: Double,
        @Query("recorddate")
        recorddate: String,
    ): Single<Result>

    @GET("search-product")
    fun searchProduct(
        @Query("name")
        name: String
    ): Single<List<Product>>
}