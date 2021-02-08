package com.example.pintiapp.di

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
class PintiApiModule {
    private val BASE_URL = "https://pinti-api.herokuapp.com"

    @Provides
    fun providePintiApi(): PintiApi {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PintiApi::class.java)
    }

    @Provides
    fun providePintiService(): PintiService{
        return PintiService()
    }

}