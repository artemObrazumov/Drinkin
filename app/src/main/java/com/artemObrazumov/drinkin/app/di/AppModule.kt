package com.artemObrazumov.drinkin.app.di

import com.artemObrazumov.drinkin.account.data.LoginMockDataSource
import com.artemObrazumov.drinkin.account.domain.data_source.LoginDataSource
import com.artemObrazumov.drinkin.account.domain.usecase.GetUserFlowUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.LoginUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.RegisterUseCase
import com.artemObrazumov.drinkin.account.domain.usecase.SaveTokensUseCase
import com.artemObrazumov.drinkin.account.domain.utls.LoginFormValidator
import com.artemObrazumov.drinkin.account.domain.utls.RegistrationFormValidator
import com.artemObrazumov.drinkin.account.presentation.login.LoginScreenViewModel
import com.artemObrazumov.drinkin.account.presentation.registration.RegistrationScreenViewModel
import com.artemObrazumov.drinkin.address.data.CafeAddressMockDataSource
import com.artemObrazumov.drinkin.address.domain.data_source.CafeAddressDataSource
import com.artemObrazumov.drinkin.address.domain.usecase.ChangeAddressUseCase
import com.artemObrazumov.drinkin.address.domain.usecase.GetAddressFlowUseCase
import com.artemObrazumov.drinkin.address.domain.usecase.GetCafesUseCase
import com.artemObrazumov.drinkin.address.presentation.addressSelect.AddressSelectViewModel
import com.artemObrazumov.drinkin.cart.data.CartMockDataSource
import com.artemObrazumov.drinkin.order.data.OrderMockDataSource
import com.artemObrazumov.drinkin.cart.domain.data_source.CartDataSource
import com.artemObrazumov.drinkin.order.domain.data_source.OrderDataSource
import com.artemObrazumov.drinkin.core.presentation.menu.MenuViewModel
import com.artemObrazumov.drinkin.product.data.data_source.ProductMockDataSource
import com.artemObrazumov.drinkin.product.domain.data_source.ProductDataSource
import com.artemObrazumov.drinkin.cart.domain.usecase.AddProductsToCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.ClearCartUseCase
import com.artemObrazumov.drinkin.product.domain.usecase.GetDashboardUseCase
import com.artemObrazumov.drinkin.product.domain.usecase.GetProductDetailUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.DecrementProductInCartUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.GetDraftOrderUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartFlowUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.GetProductsInCartUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.IncrementProductInCartUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.OrderPaymentUseCase
import com.artemObrazumov.drinkin.cart.domain.usecase.RemoveProductFromCartUseCase
import com.artemObrazumov.drinkin.cart.presentation.cart.CartScreenViewModel
import com.artemObrazumov.drinkin.order.data.OrderTrackerMockDataSource
import com.artemObrazumov.drinkin.order.domain.data_source.OrderTrackerDataSource
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderItemsFlowUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderStatusFlowUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.GetOrderUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.SaveOrderUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.UpdateOrderItemUseCase
import com.artemObrazumov.drinkin.order.domain.usecase.UpdateOrderItemsListUseCase
import com.artemObrazumov.drinkin.order.domain.utils.OrderTrackingServiceStarter
import com.artemObrazumov.drinkin.order.presentation.new_order.NewOrderScreenViewModel
import com.artemObrazumov.drinkin.order.presentation.order_details.OrderDetailsScreenViewModel
import com.artemObrazumov.drinkin.order.presentation.order_tracking.OrderTrackingServiceStarterImpl
import com.artemObrazumov.drinkin.order.presentation.orders.OrdersScreenViewModel
import com.artemObrazumov.drinkin.product.presentation.product_details.ProductDetailsViewModel
import com.artemObrazumov.drinkin.product.presentation.products_list.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // product
    singleOf(::ProductMockDataSource).bind<ProductDataSource>()
    singleOf(::GetDashboardUseCase)
    singleOf(::GetProductDetailUseCase)
    viewModelOf(::ProductListViewModel)
    viewModelOf(::ProductDetailsViewModel)

    // cart
    singleOf(::CartMockDataSource).bind<CartDataSource>()
    singleOf(::AddProductsToCartUseCase)
    singleOf(::GetProductsInCartUseCase)
    singleOf(::GetProductsInCartFlowUseCase)
    singleOf(::IncrementProductInCartUseCase)
    singleOf(::DecrementProductInCartUseCase)
    singleOf(::RemoveProductFromCartUseCase)
    singleOf(::ClearCartUseCase)
    viewModelOf(::CartScreenViewModel)

    // order
    singleOf(::OrderMockDataSource).bind<OrderDataSource>()
    singleOf(::OrderTrackerMockDataSource).bind<OrderTrackerDataSource>()
    singleOf(::OrderTrackingServiceStarterImpl).bind<OrderTrackingServiceStarter>()
    singleOf(::GetDraftOrderUseCase)
    singleOf(::OrderPaymentUseCase)
    singleOf(::UpdateOrderItemsListUseCase)
    singleOf(::UpdateOrderItemUseCase)
    singleOf(::GetOrderItemsFlowUseCase)
    singleOf(::GetOrderUseCase)
    singleOf(::SaveOrderUseCase)
    singleOf(::GetOrderStatusFlowUseCase)
    viewModelOf(::NewOrderScreenViewModel)
    viewModelOf(::OrdersScreenViewModel)
    viewModelOf(::OrderDetailsScreenViewModel)

    // address
    singleOf(::CafeAddressMockDataSource).bind<CafeAddressDataSource>()
    singleOf(::GetAddressFlowUseCase)
    singleOf(::GetCafesUseCase)
    singleOf(::ChangeAddressUseCase)
    viewModelOf(::AddressSelectViewModel)

    // account
    singleOf(::LoginFormValidator)
    singleOf(::RegistrationFormValidator)
    singleOf(::LoginMockDataSource).bind<LoginDataSource>()
    singleOf(::GetUserFlowUseCase)
    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::SaveTokensUseCase)
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::RegistrationScreenViewModel)

    viewModelOf(::MenuViewModel)
}