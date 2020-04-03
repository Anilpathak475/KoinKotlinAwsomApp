package com.anil.hse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anil.hse.R
import com.anil.hse.persistance.entitiy.Cart
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter(
    val onRemove: (Cart) -> Unit,
    val onSelected: (Cart) -> Unit,
    val onAdded: (Cart) -> Unit
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    var cartItems = emptyList<Cart>()

    fun setCartDetails(cartItems: List<Cart>) {
        this.cartItems = cartItems
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(layoutInflater.inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(vh: CartViewHolder, position: Int) {
        val cart = cartItems[position]
        vh.itemView.textViewCartProductName.text = cart.productName
        vh.itemView.textViewQuantity.text = cart.quantity.toString()
        vh.itemView.textViewCartPrice.text = vh.itemView.resources.getString(
            R.string.price,
            cart.price
        )
        Glide
            .with(vh.itemView)
            .load(
                vh.itemView.resources.getString(
                    R.string.imageUrl,
                    cart.imageUrl
                )
            )
            .fitCenter()
            .placeholder(R.drawable.loading)
            .into(vh.itemView.imageViewCartProduct)
        vh.itemView.setOnClickListener { this.onSelected(cart) }
        vh.itemView.textviewAdd.setOnClickListener { this.onAdded(cart) }
        vh.itemView.textviewRemove.setOnClickListener { this.onRemove(cart) }

    }

    override fun getItemCount() = cartItems.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}