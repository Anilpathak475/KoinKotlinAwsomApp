package com.anil.hse.networking

import com.anil.hse.model.Category
import com.anil.hse.model.Product
import com.anil.hse.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HseService {

    @GET("ext-api/app/1/category/tree")
    suspend fun fetchCategoryTree(): Category

    @GET("ext-api/app/1/c/**/*-{categoryId}")
    suspend fun fetchProductsCategory(
        @Path("categoryId") categoryId: String,
        @Query("page", encoded = true) page: Int = 1,
        @Query("pageSize", encoded = true) pageSize: Int = 10
    ): ProductResponse

    @GET("ext-api/app/1/product/{productId}")
    suspend fun fetchProductsDetails(@Path("productId") productId: String): Product
}
