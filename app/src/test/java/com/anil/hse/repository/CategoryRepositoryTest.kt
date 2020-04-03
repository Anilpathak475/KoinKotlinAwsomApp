package com.anil.hse.repository

import com.anil.hse.model.Category
import com.anil.hse.networking.HseService
import com.anil.hse.networking.Resource
import com.anil.hse.networking.ResponseHandler
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException

@RunWith(JUnit4::class)
class CategoryRepositoryTest {
    private val responseHandler = ResponseHandler()
    private lateinit var hseService: HseService
    private lateinit var repository: CategoryRepository
    private val category = Category()
    private val successResponse = Resource.success(category)


    @Before
    fun setUp() {
        hseService = mock()
        val mockException: HttpException = mock()
        whenever(mockException.code()).thenReturn(401)
        runBlocking {
            whenever(hseService.fetchCategoryTree()).thenReturn(category)
        }
        repository = CategoryRepository(
            hseService,
            responseHandler
        )
    }

    @Test
    fun `test get categories requested, then categories is returned`() =
        runBlocking {
            assertEquals(successResponse, repository.loadCategories())
        }
}