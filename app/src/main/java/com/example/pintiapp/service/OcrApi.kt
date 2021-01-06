package com.example.pintiapp.service

import com.example.pintiapp.models.OcrResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OcrApi {

    @GET("ocr")
    fun getPrice(
        @Query("url")
        url: String
    ): Single<OcrResult>

}