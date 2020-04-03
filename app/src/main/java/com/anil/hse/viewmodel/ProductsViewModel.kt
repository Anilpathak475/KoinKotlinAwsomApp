package com.anil.hse.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.anil.hse.base.LiveCoroutinesViewModel
import com.anil.hse.model.Product
import com.anil.hse.networking.Resource
import com.anil.hse.persistance.entitiy.Cart
import com.anil.hse.repository.CartRepository
import com.anil.hse.repository.ProductRepository
import kotlinx.coroutines.Dispatchers

class ProductsViewModel constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : LiveCoroutinesViewModel() {

    private val loadProduct
            by lazy { MutableLiveData<Boolean>() }

    private val productId
            by lazy { MutableLiveData<String>() }
    val cartNotification
            by lazy { MutableLiveData<String>() }


    val products by lazy {
        productRepository.loadProductByCategories()
    }
    var product = productId.switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            productId.value?.let {
                emit(productRepository.loadProductDetails(productId = it))
            }
        }
    }

    val cart: LiveData<List<Cart>> by lazy {
        this.loadProduct.switchMap {
            launchOnViewModelScope {
                this.cartRepository.load()
            }
        }
    }

    init {
        this.loadProduct.postValue(true)
    }

    fun setCategory(catId: String) =
        productRepository.setCatId(catId)

    fun addItemInCart(product: Product, quantity: Int, result: (String) -> Unit) {
        val isAlreadyAdded =
            cart.value?.firstOrNull { cartEntity -> cartEntity.productId == product.sku }
        isAlreadyAdded?.let {
            result("Already In Database")
        } ?: run {
            cartRepository.add(createNewCartItem(product, quantity))
            result("Success")
        }
    }

    fun fetchProductProductDetail(productId: String) = this.productId.postValue(productId)

    private fun createNewCartItem(product: Product, quantity: Int) =
        Cart(
            productId = product.sku,
            productName = product.nameShort,
            price = product.productPrice.price.toString(),
            quantity = quantity,
            imageUrl = product.imageUris.first(),
            time = System.currentTimeMillis()
        )
}
