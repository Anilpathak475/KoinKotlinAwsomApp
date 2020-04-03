package com.anil.hse.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.anil.hse.base.LiveCoroutinesViewModel
import com.anil.hse.networking.Resource
import com.anil.hse.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers

class CategoryViewModel constructor(
    private val categoryRepository: CategoryRepository
) : LiveCoroutinesViewModel() {
    private var loadCategoryData: MutableLiveData<Boolean> = MutableLiveData()

    var categories = loadCategoryData.switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(categoryRepository.loadCategories())
        }
    }

    fun fetchCategories() = loadCategoryData.postValue(true)
}
