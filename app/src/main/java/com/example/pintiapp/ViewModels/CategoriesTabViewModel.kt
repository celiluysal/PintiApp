package com.example.pintiapp.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Category
import com.example.pintiapp.service.PintiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoriesTabViewModel : ViewModel() {
    @Inject
    lateinit var pintiService: PintiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposible = CompositeDisposable()

    val categories = MutableLiveData<List<Category>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCategories()
    }

    private fun fetchCategories() {
        loading.value = true
        disposible.add(
                pintiService.getCategories()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object: DisposableSingleObserver<List<Category>>() {
                            override fun onSuccess(value: List<Category>?) {
                                value.let { category ->
                                    categories.value = category
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