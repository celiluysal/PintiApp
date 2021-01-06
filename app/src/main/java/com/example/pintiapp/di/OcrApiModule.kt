package com.example.pintiapp.di

import com.example.pintiapp.service.OcrApi
import com.example.pintiapp.service.OcrService
import com.example.pintiapp.service.PintiApi
import com.example.pintiapp.service.PintiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class OcrApiModule {
    private val BASE_URL = "https://pinti-ocr.herokuapp.com"

    @Provides
    fun providePintiOcrApi(): OcrApi {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(OcrApi::class.java)
    }

    @Provides
    fun providePintiOcrService(): OcrService {
        return OcrService()
    }

}

