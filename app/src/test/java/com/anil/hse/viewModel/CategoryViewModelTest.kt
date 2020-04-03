package com.anil.hse.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anil.hse.base.Coroutines
import com.anil.hse.model.Category
import com.anil.hse.networking.Resource
import com.anil.hse.repository.CategoryRepository
import com.anil.hse.viewmodel.CategoryViewModel
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
class CategoryViewModelTest {
    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryObserver: Observer<Resource<Category>>
    private val categorySuccessResource = Resource.success(Category())

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
        categoryRepository = mock()
        runBlocking {
            whenever(categoryRepository.loadCategories()).thenReturn(
                categorySuccessResource
            )
        }
        viewModel = CategoryViewModel(categoryRepository)
        categoryObserver = mock()
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when loadCategories  is called, then observer is updated with success`() {
        viewModel.categories.observeForever(categoryObserver)
        Coroutines.ioThenMain({
            viewModel.fetchCategories()
            delay(10)
            verify(categoryRepository).loadCategories()
        }, {
            verify(categoryObserver, timeout(50)).onChanged(Resource.loading(null))
            verify(categoryObserver, timeout(50)).onChanged(categorySuccessResource)
        })
    }
}