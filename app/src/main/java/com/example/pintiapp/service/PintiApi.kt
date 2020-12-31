package com.example.pintiapp.service

import com.example.pintiapp.models.Category
import com.example.pintiapp.models.Product
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
}