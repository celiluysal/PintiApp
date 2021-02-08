package com.example.pintiapp.ui.categories.shops_tab

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Shop
import com.example.pintiapp.service.PintiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShopsTabViewModel : ViewModel() {
    @Inject
    lateinit var pintiService: PintiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposible = CompositeDisposable()

    val shops = MutableLiveData<List<Shop>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchShops()
    }

    private fun fetchShops() {
        loading.value = true
        disposible.add(
                pintiService.getShops()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object: DisposableSingleObserver<List<Shop>>() {
                            override fun onSuccess(value: List<Shop>?) {
                                value.let { shop ->
                                    shops.value = shop
                                    loadError.value = false
                                    loading.value = false
                                }
                                if (value.isNullOrEmpty()){
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