package com.example.pintiapp.di

import com.example.pintiapp.service.OcrService
import com.example.pintiapp.service.PintiService
import com.example.pintiapp.ui.add_product.AddProductViewModel
import com.example.pintiapp.ui.categories.categories_tab.CategoriesTabViewModel
import com.example.pintiapp.ui.categories.categories_tab.product_by_category.ProductByCategoryViewModel
import com.example.pintiapp.ui.categories.search.SearchViewModel
import com.example.pintiapp.ui.categories.shops_tab.ShopsTabViewModel
import com.example.pintiapp.ui.categories.shops_tab.product_by_shop.ProductByShopViewModel
import com.example.pintiapp.ui.home.HomePageViewModel
import com.example.pintiapp.ui.scan_barcode.BarcodeScanViewModel
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.ShopStatic
import dagger.Component

@Component(modules = [PintiApiModule::class,OcrApiModule::class])
interface ApiComponent {
    fun inject(service: PintiService)
    fun inject(service: OcrService)

    fun inject(staticClass: CategoryStatic)
    fun inject(staticClass: ShopStatic)

    fun inject(viewModel: HomePageViewModel)
    fun inject(viewModel: CategoriesTabViewModel)
    fun inject(viewModel: ShopsTabViewModel)
    fun inject(viewModel: ProductByCategoryViewModel)
    fun inject(viewModel: ProductByShopViewModel)
    fun inject(viewModel: BarcodeScanViewModel)
    fun inject(viewModel: AddProductViewModel)
    fun inject(viewModel: SearchViewModel)
}