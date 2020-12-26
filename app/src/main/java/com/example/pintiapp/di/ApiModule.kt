package com.example.pintiapp.di

import com.example.pintiapp.service.PintiApi
import com.example.pintiapp.service.PintiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://pinti-api.herokuapp.com"

    @Provides
    fun providePintiApi(): PintiApi {
        return Retrofit.Builder()
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