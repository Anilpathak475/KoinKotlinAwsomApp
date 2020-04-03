package com.anil.hse.model


data class Product(
    val additionalInformation: String? = "",
    val averageStars: Double = 0.0,
    val brandId: String = "",
    val brandNameLong: String = "",
    val brandNameShort: String? = "",
    val categoryCode: String? = "",
    val deliveryDate: Any? = "",
    val deliveryText: String? = "",
    val deliveryTimeDays: Any? = "",
    val deliveryType: Any? = "",
    val energyFactor: Any? = "",
    val fromPrice: Boolean? = false,
    val hazardWarning: Any? = "",
    val imageUris: List<String> = emptyList(),
    val ingredients: Any? = "",
    val legalText: String? = "",
    val longDescription: String? = "",
    val nameShort: String = "",
    val notAllowedInCountry: Boolean = false,
    val picCount: Int? = 0,
    val productPrice: ProductPrice,
    val reviewers: Int? = 0,
    val reviewsForbidden: Boolean? = false,
    val sku: String = "",
    val status: String = "",
    val stockAmount: Int? = 0,
    val stockColor: String? = "",
    val title: String? = "",
    val usps: List<String>? = emptyList(),
    val variations: List<Variation> = emptyList()


)

data class Variation(
    val imageUris: List<String>,
    val picCount: Int,
    val productPrice: ProductPrice,
    val sku: String,
    val status: String,
    val stockAmount: Int,
    val stockColor: String,
    val variationType: String
)

data class ProductPrice(
    val basePrice: String = "",
    val basePriceInfo: Any? = "",
    val basePriceRefAmount: Any? = "",
    val basePriceUnit: Any? = "",
    val country: Any? = "",
    val currency: String? = "",
    val percentDiscount: String? = "",
    val price: Double = 0.0,
    val priceDiscount: Float = 0f,
    val priceLabel: String = "",
    val priceValidTo: String = "",
    val referencePrice: Double = 0.0,
    val referencePriceLabel: String = "",
    val shippingCosts: Double = 0.0
)