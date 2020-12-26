package com.example.pintiapp.di

import com.example.pintiapp.service.PintiService
import com.example.pintiapp.viewModels.HomePageViewModel
import com.example.pintiapp.viewModels.ProfilePageViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: PintiService)

    fun inject(viewModel: HomePageViewModel)
}