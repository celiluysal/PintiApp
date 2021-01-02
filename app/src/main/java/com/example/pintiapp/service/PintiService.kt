package com.example.pintiapp.service

import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Category
import com.example.pintiapp.models.Product
import com.example.pintiapp.models.Result
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

    fun addProduct(barcode: String, brand: String, categoryid: String,
                   photourl: String, name: String): Single<Result> {

        return api.addProduct(barcode, brand, categoryid, photourl, name)
    }

    fun addRecord(barcode: String, ownerid: String, ownername: String,
                  shopid: String, locationtitle: String, locationcoordinate: String,
                  price: Double, recorddate: String): Single<Result> {

        return api.addRecord(barcode, ownerid, ownername, shopid,
            locationtitle, locationcoordinate, price, recorddate)
    }
}