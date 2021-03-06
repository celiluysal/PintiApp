package com.example.pintiapp.ui.categories.shops_tab.product_by_shop

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

class ProductByShopViewModel : ViewModel() {
    @Inject
    lateinit var pintiService: PintiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposible = CompositeDisposable()

    val products = MutableLiveData<List<Product>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    companion object{
        lateinit var id: String
    }

    fun refresh(shopId: String = id) {
        id = shopId
        fetchProducts(id)
    }

    private fun fetchProducts(shopId: String) {
        loading.value = true
        disposible.add(
            pintiService.getProductsByShopId(shopId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        value.let { product ->
                            for(v in value!!) {
                                v.records = v.records.sortedBy { it.price }
                            }
                            products.value = product
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