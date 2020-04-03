package com.anil.hse.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.anil.hse.model.Product

class HseDataSourceFactory(
    private val hseDataSource: HseDataSource
) : DataSource.Factory<Int, Product>() {
    val hseDataSourceLiveData = MutableLiveData<HseDataSource>()

    override fun create(): DataSource<Int, Product> {
        hseDataSourceLiveData.postValue(hseDataSource)
        return hseDataSource
    }

    fun setCatId(catId: String) {
        hseDataSource.catId = catId
    }
}