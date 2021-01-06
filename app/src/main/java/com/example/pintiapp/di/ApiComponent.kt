package com.example.pintiapp.di

import com.example.pintiapp.service.OcrService
import com.example.pintiapp.service.PintiService
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.ShopStatic
import com.example.pintiapp.viewModels.*
import dagger.Component

@Component(modules = [ApiModule::class,OcrApiModule::class])
interface ApiComponent {
    fun inject(service: PintiService)
    fun inject(service: OcrService)

    fun inject(staticClass: CategoryStatic)
    fun inject(staticClass: ShopStatic)

    fun inject(viewModel: HomePageViewModel)
    fun inject(viewModel: CategoriesTabViewModel)
    fun inject(viewModel: MarketsTabViewModel)
    fun inject(viewModel: ProductByCategoryViewModel)
    fun inject(viewModel: ProductByShopViewModel)
    fun inject(viewModel: BarcodeScanViewModel)
    fun inject(viewModel: AddProductViewModel)
    fun inject(viewModel: SearchViewModel)
}