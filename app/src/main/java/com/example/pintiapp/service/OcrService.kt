package com.example.pintiapp.service

import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.OcrResult
import io.reactivex.Single
import javax.inject.Inject

class OcrService {

    @Inject
    lateinit var api: OcrApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getPrice(url: String): Single<OcrResult> {
        return api.getPrice(url)
    }

}