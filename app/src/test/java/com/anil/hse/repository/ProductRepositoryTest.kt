package com.anil.hse.repository

import com.anil.hse.datasource.HseDataSourceFactory
import com.anil.hse.model.Product
import com.anil.hse.model.ProductPrice
import com.anil.hse.model.ProductResponse
import com.anil.hse.networking.HseService
import com.anil.hse.networking.Resource
import com.anil.hse.networking.ResponseHandler
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import retrofit2.HttpException

@RunWith(JUnit4::class)
class ProductRepositoryTest {
    private val responseHandler = ResponseHandler()
    private lateinit var hseService: HseService
    private lateinit var hseDataSource: HseDataSourceFactory
    private lateinit var repository: ProductRepository
    private val productResponse = ProductResponse()
    private val categoryId = "12345678"
    private val validProductId = "12345678"
    private val invalidProductId = "some product Id"
    private val product = Product(
        sku = validProductId,
        productPrice = ProductPrice()
    )
    private val successResponse = Resource.success(product)
    private val errorResponse = Resource.error("Unauthorised", null)


    @Before
    fun setUp() {
        hseService = mock()
        hseDataSource = mock()
        val mockException: HttpException = mock()
        whenever(mockException.code()).thenReturn(401)
        runBlocking {
            whenever(
                hseService.fetchProductsCategory(
                    eq(categoryId),
                    Mockito.anyInt(),
                    Mockito.anyInt()
                )
            ).thenReturn(productResponse)
            whenever(
                hseService.fetchProductsDetails(
                    eq(invalidProductId)
                )
            ).thenThrow(
                mockException
            )
            whenever(
                hseService.fetchProductsDetails(
                    eq(validProductId)
                )
            ).thenReturn(
                product
            )
        }
        repository = ProductRepository(
            hseService,
            responseHandler,
            hseDataSource
        )
    }

    @Test
    fun `test fetchProductsCategory when valid category is requested, then products are returned`() =
        runBlocking {
            val response = hseService.fetchProductsCategory(categoryId)
            assertEquals(productResponse, response)
        }

    @Test
    fun `test fetchProduct when valid product is requested, then product is returned`() =
        runBlocking {
            val response = repository.loadProductDetails(validProductId)
            assertEquals(successResponse, response)
        }

    @Test
    fun `test fetchProduct when invalid product is requested, then error in returned`() =
        runBlocking {
            val response = repository.loadProductDetails(invalidProductId)
            assertEquals(errorResponse, response)
        }
}