package com.anil.hse.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.anil.hse.base.LiveCoroutinesViewModel
import com.anil.hse.persistance.entitiy.Cart
import com.anil.hse.repository.CartRepository

class CartViewModel constructor(
    private val cartRepository: CartRepository
) : LiveCoroutinesViewModel() {

    val cart: LiveData<List<Cart>>
    private var loadCartData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        cart = this.loadCartData.switchMap {
            launchOnViewModelScope {
                this.cartRepository.load()
            }
        }
    }

    fun loadCart() = this.loadCartData.postValue(true)
    fun updateCart(cart: Cart) =
        cartRepository.update(cart)

    fun checkout() {
        cart.value?.apply {
            cartRepository.checkout(this)
        }
    }

    fun clearCart() {
        cart.value?.let {
            cartRepository.clear(it)
        }
    }
}
