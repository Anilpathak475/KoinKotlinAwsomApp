package com.anil.hse.di

import com.anil.hse.viewmodel.CartViewModel
import com.anil.hse.viewmodel.CategoryViewModel
import com.anil.hse.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductsViewModel(get(), get()) }
    viewModel { CartViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
}
