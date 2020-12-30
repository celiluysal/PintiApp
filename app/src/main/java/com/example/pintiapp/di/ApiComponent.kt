package com.example.pintiapp.di

import com.example.pintiapp.service.PintiService
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.ShopStatic
import com.example.pintiapp.viewModels.*
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: PintiService)

    fun inject(viewModel: HomePageViewModel)
    fun inject(viewModel: CategoriesTabViewModel)
    fun inject(viewModel: MarketsTabViewModel)
    fun inject(staticClass: CategoryStatic)
    fun inject(staticClass: ShopStatic)
    fun inject(staticClass: ProductByCategoryViewModel)
    fun inject(staticClass: ProductByShopViewModel)
}