package com.example.pintiapp.viewModels

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

class ProductByCategoryViewModel : ViewModel() {
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

    fun refresh(categoryId: String = id) {
        id = categoryId
        fetchProducts(id)
    }

    private fun fetchProducts(categoryId: String) {
        loading.value = true
        disposible.add(
            pintiService.getProductsByCategoryId(categoryId)
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