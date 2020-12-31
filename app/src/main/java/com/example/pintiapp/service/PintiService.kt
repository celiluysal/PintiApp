package com.example.pintiapp.service

import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Category
import com.example.pintiapp.models.Product
import com.example.pintiapp.models.Shop
import io.reactivex.Single
import javax.inject.Inject

class PintiService {

    @Inject
    lateinit var api: PintiApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getProducts(): Single<List<Product>> {
        return api.fetchLastProducts()
    }

    fun getShops(): Single<List<Shop>> {
        return api.fetchShops()
    }

    fun getCategories(): Single<List<Category>> {
        return api.fetchCategories()
    }

    fun getProductsByShopId(shopId: String): Single<List<Product>> {
        return api.fetchProductsByShopId(shopId)
    }

    fun getProductsByCategoryId(categoryId: String): Single<List<Product>> {
        return api.fetchProductsByCategoryId(categoryId)
    }

    fun findProduct(barcode: String): Single<List<Product>> {
        return api.findProduct(barcode)
    }
}