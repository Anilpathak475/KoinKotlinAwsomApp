package com.anil.hse.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anil.hse.base.Coroutines
import com.anil.hse.model.Product
import com.anil.hse.model.ProductPrice
import com.anil.hse.networking.Resource
import com.anil.hse.persistance.entitiy.Cart
import com.anil.hse.repository.CartRepository
import com.anil.hse.repository.ProductRepository
import com.anil.hse.viewmodel.ProductsViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ProductViewModelTest {
    private lateinit var viewModel: ProductsViewModel
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var productObserver: Observer<Resource<Product>>
    private lateinit var cartObserver: Observer<Resource<List<Cart>>>
    private val validProductId = "321234"
    private val invalidProductInt = "qwerty"
    private val productSuccessResource = Resource.success(
        Product(
            sku = validProductId,
            productPrice = ProductPrice()
        )
    )
    private val errorResource = Resource.error("Unauthorised", null)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        productRepository = mock()
        cartRepository = mock()
        runBlocking {
            whenever(productRepository.loadProductDetails(validProductId)).thenReturn(
                productSuccessResource
            )
            whenever(productRepository.loadProductDetails(invalidProductInt)).thenReturn(
                errorResource
            )
        }
        viewModel = ProductsViewModel(productRepository, cartRepository)
        productObserver = mock()
        cartObserver = mock()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when getProduct is called with valid product, then observer is updated with success`() {
        viewModel.product.observeForever(productObserver)
        Coroutines.ioThenMain({
            viewModel.fetchProductProductDetail(validProductId)
            delay(10)
            verify(productRepository).loadProductDetails(validProductId)
        }, {
            verify(productObserver, timeout(50)).onChanged(Resource.loading(null))
            verify(productObserver, timeout(50)).onChanged(productSuccessResource)
        })
    }

    @Test
    fun `when getProduct is called with invalid location, then observer is updated with failure`() {
        viewModel.product.observeForever(productObserver)
        Coroutines.ioThenMain({
            viewModel.fetchProductProductDetail(invalidProductInt)
            delay(10)
            verify(productRepository).loadProductDetails(invalidProductInt)
        }, {
            verify(productObserver, timeout(50)).onChanged(Resource.loading(null))
            verify(productObserver, timeout(50)).onChanged(errorResource)
        })
    }
}