package com.artemObrazumov.drinkin.app.di

import com.artemObrazumov.drinkin.address.data.CafeAddressMockDataSource
import com.artemObrazumov.drinkin.address.domain.data_source.CafeAddressDataSource
import com.artemObrazumov.drinkin.address.domain.usecase.ChangeAddressUseCase
import com.artemObrazumov.drinkin.address.domain.usecase.GetAddressFlowUseCase
import com.artemObrazumov.drinkin.address.domain.usecase.GetCafesUseCase
import com.artemObrazumov.drinkin.address.presentation.addressSelect.AddressSelectViewModel
import com.artemObrazumov.drinkin.cart.data.CartMockDataSource
import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource
import com.artemObrazumov.drinkin.core.presentation.menu.MenuViewModel
import com.artemObrazumov.drinkin.product.data.data_source.ProductMockDataSource
import com.artemObrazumov.drinkin.product.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.cart.domain.usecase.AddProductsToCartUseCase
import com.artemObrazumov.drinkin.product.domain.usecase.GetDashboardUseCase
import com.artemObrazumov.drinkin.product.domain.usecase.GetProductDetailUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.DecrementProductInCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartFlowUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.IncrementProductInCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.RemoveProductFromCartUseCase
import com.artemObrazumov.drinkin.cart.presentation.cart.CartScreenViewModel
import com.artemObrazumov.drinkin.product.presentation.product_details.ProductDetailsViewModel
import com.artemObrazumov.drinkin.product.presentation.products_list.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::ProductMockDataSource).bind<ProductDataSource>()
    singleOf(::GetDashboardUseCase)
    singleOf(::GetProductDetailUseCase)
    viewModelOf(::ProductListViewModel)
    viewModelOf(::ProductDetailsViewModel)

    singleOf(::CartMockDataSource).bind<CartDataSource>()
    singleOf(::AddProductsToCartUseCase)
    singleOf(::GetProductsInCartUseCase)
    singleOf(::GetProductsInCartFlowUseCase)
    singleOf(::IncrementProductInCartUseCase)
    singleOf(::DecrementProductInCartUseCase)
    singleOf(::RemoveProductFromCartUseCase)
    viewModelOf(::CartScreenViewModel)

    singleOf(::CafeAddressMockDataSource).bind<CafeAddressDataSource>()
    singleOf(::GetAddressFlowUseCase)
    singleOf(::GetCafesUseCase)
    singleOf(::ChangeAddressUseCase)
    viewModelOf(::AddressSelectViewModel)

    viewModelOf(::MenuViewModel)
}