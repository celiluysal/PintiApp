package com.example.pintiapp.di

import com.example.pintiapp.service.PintiService
import com.example.pintiapp.viewModels.CategoriesTabViewModel
import com.example.pintiapp.viewModels.HomePageViewModel
import com.example.pintiapp.viewModels.MarketsTabViewModel
import com.example.pintiapp.viewModels.ProfilePageViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: PintiService)

    fun inject(viewModel: HomePageViewModel)
    fun inject(viewModel: CategoriesTabViewModel)
    fun inject(viewModel: MarketsTabViewModel)
}