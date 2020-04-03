package com.anil.hse.repository

import com.anil.hse.base.Coroutines
import com.anil.hse.persistance.CartDao
import com.anil.hse.persistance.entitiy.Cart

class CartRepository constructor(
    private val cartDao: CartDao
) {

    fun load() =
        cartDao.getCartItems()

    fun add(cart: Cart) =
        Coroutines.io {
            cartDao.insert(cart)
        }

    private fun delete(cart: Cart) =
        Coroutines.io {
            cartDao.delete(cart)
        }

    private fun delete(items: List<Cart>) =
        Coroutines.io {
            cartDao.delete(items)
        }

    fun update(cart: Cart) {
        if (cart.quantity > 0)
            add(cart)
        else
            delete(cart)
    }

    fun checkout(cartItems: List<Cart>) {
        cartItems.apply {
            Coroutines.io {
                forEach { it.isCheckOutDone = true }
                cartDao.update(this)
            }
        }
    }

    fun clear(cartItems: List<Cart>) {
        delete(cartItems)
    }
}