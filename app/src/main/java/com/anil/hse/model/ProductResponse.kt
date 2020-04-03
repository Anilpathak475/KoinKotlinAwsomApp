package com.anil.hse.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val activeSorting: ActiveSorting? = null,
    val brands: List<Brand> = emptyList(),
    val cachingForbidden: Boolean? = false,
    val categories: List<Category>? = emptyList(),
    val displayName: String = "",
    val filter: Filter? = null,
    val paging: Paging? = null,
    @SerializedName("productResults")
    val products: List<Product> = emptyList(),
    val resultCount: Int = 0,
    val sortings: List<Sorting> = emptyList(),
    val topShop: String = ""
)

data class Paging(
    val numPages: Int,
    val page: Int,
    val pageSize: Int
)

data class Sorting(
    val displayName: String,
    val name: String,
    val order: String,
    val sortType: String
)


data class Brand(
    val brandId: String,
    val children: Any,
    val displayName: String,
    val resultCount: Int
)


data class ActiveSorting(
    val displayName: Any,
    val name: String,
    val order: String,
    val sortType: String
)

data class Filter(
    val filterGroups: List<FilterGroup>,
    val resetLink: String,
    val selectedFilterItems: List<Any>,
    val selectedItemCount: Int
)

data class FilterGroup(
    val displayName: String,
    val displayType: Any,
    val fieldName: String,
    val filterItems: List<FilterItem>,
    val filterName: String,
    val hidden: Boolean,
    val name: String,
    val resetLink: String
)

data class FilterItem(
    val childCount: Int,
    val displayName: String,
    val filterName: String,
    val filterValue: String,
    val iconImgUrl: Any,
    val id: Any,
    val level: Int,
    val link: String,
    val name: String,
    val resetLink: String,
    val resultCount: Int,
    val rgbCode: Any,
    val selected: Boolean
)