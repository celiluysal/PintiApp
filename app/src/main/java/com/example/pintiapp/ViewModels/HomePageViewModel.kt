package com.example.pintiapp.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Products
import com.example.pintiapp.service.PintiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePageViewModel : ViewModel() {

    @Inject
    lateinit var pintiService: PintiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposible = CompositeDisposable()

    val products = MutableLiveData<Products>()
    val productsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchProducts()
    }

    private fun fetchProducts() {
        Log.e("fbg","fetch")
        loading.value = true
        disposible.add(
            pintiService.getProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Products>() {
                    override fun onSuccess(value: Products?) {
                        Log.e("fbg","on success ---------")

                        products.value = value
                        Log.e("fbg",value!!.products[0]!!.name!!.toString())

                        productsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("fbg","on error ---------")
                        Log.e("fbg", e.toString())

                        productsLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposible.clear()
    }

}