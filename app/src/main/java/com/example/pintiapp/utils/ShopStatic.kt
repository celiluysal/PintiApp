package com.example.pintiapp.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Category
import com.example.pintiapp.models.Shop
import com.example.pintiapp.service.PintiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShopStatic {

    companion object {
        var shared = ShopStatic()
    }

    var shops = MutableLiveData<List<Shop>>()

    private fun setShops(shopList: List<Shop>) {
        shops.value = shopList
    }

    fun getShopName(id: String): String {
        if (!shops.value.isNullOrEmpty()) {
            val shop = shops.value!!.find { shop -> id == shop.shopId }
            if (shop != null)
                return shop.shopName
        }
        return "Bulunamadı"
    }



    @Inject
    lateinit var pintiService: PintiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposible = CompositeDisposable()

    fun refresh() {
        fetchShops()
    }

    private fun fetchShops() {
        disposible.add(
                pintiService.getShops()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object: DisposableSingleObserver<List<Shop>>() {
                            override fun onSuccess(value: List<Shop>?) {
                                value?.let { setShops(it) }
                            }

                            override fun onError(e: Throwable?) {
                                Log.e("api error: ", e.toString())

                            }

                        })
        )

    }
}