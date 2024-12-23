package com.artemObrazumov.drinkin.di

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
    singleOf(::CartMockDataSource).bind<CartDataSource>()
    singleOf(::GetDashboardUseCase)
    singleOf(::GetProductDetailUseCase)
    singleOf(::AddProductsToCartUseCase)
    singleOf(::GetProductsInCartUseCase)
    singleOf(::GetProductsInCartFlowUseCase)
    singleOf(::IncrementProductInCartUseCase)
    singleOf(::DecrementProductInCartUseCase)
    singleOf(::RemoveProductFromCartUseCase)

    viewModelOf(::ProductListViewModel)
    viewModelOf(::ProductDetailsViewModel)
    viewModelOf(::CartScreenViewModel)
    viewModelOf(::MenuViewModel)
}