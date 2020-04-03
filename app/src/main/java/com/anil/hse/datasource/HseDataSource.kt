package com.anil.hse.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.anil.hse.base.Coroutines
import com.anil.hse.model.Product
import com.anil.hse.networking.HseService
import com.anil.hse.networking.ResponseHandler

enum class State {
    DONE, LOADING, ERROR
}

class HseDataSource(
    private val hseService: HseService,
    private val responseHandler: ResponseHandler
) : PageKeyedDataSource<Int, Product>() {
    var state: MutableLiveData<State> = MutableLiveData()
    var catId: String = ""
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Product>
    ) {
        updateState(State.LOADING)
        try {
            Coroutines.ioThenMain({
                hseService.fetchProductsCategory(catId)
            }, {
                it?.let {
                    responseHandler.handleSuccess(it).data?.products?.let { products ->
                        callback.onResult(
                            products,
                            null,
                            2
                        )
                    }
                } ?: run {
                    updateState(State.ERROR)
                }
            })
        } catch (ex: Exception) {
            updateState(State.ERROR)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
        updateState(State.LOADING)
        try {
            Coroutines.ioThenMain({
                hseService.fetchProductsCategory(catId, params.key, params.requestedLoadSize)
            }, {
                it?.let {
                    responseHandler.handleSuccess(it).data?.let { products ->
                        if (products.resultCount / 10 > params.key)
                            updateState(State.DONE)
                        callback.onResult(
                            products.products,
                            params.key + 1
                        )
                    }
                } ?: run {
                    updateState(State.ERROR)
                }
            })
        } catch (ex: Exception) {
            updateState(State.ERROR)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}