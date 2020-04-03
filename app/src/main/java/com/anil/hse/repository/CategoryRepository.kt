package com.anil.hse.repository

import com.anil.hse.model.Category
import com.anil.hse.networking.HseService
import com.anil.hse.networking.Resource
import com.anil.hse.networking.ResponseHandler

class CategoryRepository constructor(
    private val hseService: HseService,
    private val responseHandler: ResponseHandler
) {
    suspend fun loadCategories(): Resource<Category> {
        return try {
            val response = hseService.fetchCategoryTree()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }


}
