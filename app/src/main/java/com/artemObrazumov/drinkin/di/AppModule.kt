package com.artemObrazumov.drinkin.di

import com.artemObrazumov.drinkin.dashboard.data.data_source.ProductMockDataSource
import com.artemObrazumov.drinkin.dashboard.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.dashboard.domain.usecase.GetDashboardUseCase
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::ProductMockDataSource).bind<ProductDataSource>()
    singleOf(::GetDashboardUseCase)

    viewModelOf(::ProductListViewModel)
}