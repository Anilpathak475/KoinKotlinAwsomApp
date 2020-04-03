package com.anil.hse.di

import com.anil.hse.datasource.HseDataSource
import com.anil.hse.datasource.HseDataSourceFactory
import com.anil.hse.networking.HseService
import com.anil.hse.networking.ResponseHandler
import com.anil.hse.repository.CartRepository
import com.anil.hse.repository.CategoryRepository
import com.anil.hse.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CategoryRepository(get(), get()) }
    single { ProductRepository(get(), get(), get()) }
    single { CartRepository(get()) }
    single { provideHseDataSource(get(), get()) }
    single { provideHseDataSourceFactory(get()) }

}

private fun provideHseDataSource(
    hseService: HseService,
    responseHandler: ResponseHandler
): HseDataSource = HseDataSource(hseService, responseHandler)

private fun provideHseDataSourceFactory(hseDataSource: HseDataSource): HseDataSourceFactory =
    HseDataSourceFactory(hseDataSource)
