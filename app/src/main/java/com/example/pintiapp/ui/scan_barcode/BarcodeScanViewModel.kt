package com.example.pintiapp.ui.scan_barcode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Product
import com.example.pintiapp.service.PintiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BarcodeScanViewModel : ViewModel() {
    @Inject
    lateinit var pintiService: PintiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposible = CompositeDisposable()

    val products = MutableLiveData<List<Product>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun fetchData(barcode: String) {
        fetchProducts(barcode)
    }

    private fun fetchProducts(barcode: String) {
        loading.value = true
        disposible.add(
            pintiService.findProduct(barcode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        if (value != null) {
                            products.value = value
                            loadError.value = false
                            loading.value = false
                        } else {
                            loadError.value = true
                            loading.value = false
                        }
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("api error: ", e.toString())
                        loadError.value = true
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