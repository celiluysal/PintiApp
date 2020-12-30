package com.example.pintiapp.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.Category
import com.example.pintiapp.service.PintiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CategoryStatic {
    companion object {
        var shared = CategoryStatic()
    }

    var categories = MutableLiveData<List<Category>>()

    fun setCategories(categoryList: List<Category>) {
        categories.value = categoryList
    }

    fun getCategoryName(id: String): String {
        if (categories.value?.isNotEmpty() == true) {
            val category = categories.value!!.find { category -> id == category.categoryId }
            if (category != null)
                return category.categoryName
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
        fetchCategories()
    }

    private fun fetchCategories() {
        disposible.add(
                pintiService.getCategories()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object: DisposableSingleObserver<List<Category>>() {
                            override fun onSuccess(value: List<Category>?) {
                                value?.let { setCategories(it) }
                            }

                            override fun onError(e: Throwable?) {
                                Log.e("api error: ", e.toString())

                            }

                        })
        )

    }


}
