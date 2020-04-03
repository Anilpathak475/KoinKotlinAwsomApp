package com.anil.hse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anil.hse.R
import com.anil.hse.model.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(
    val onSelected: (Product) -> Unit,
    val addToCart: (Product) -> Unit
) :
    PagedListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(vh: ProductViewHolder, position: Int) {
        getItem(position)?.let { product ->
            vh.itemView.textViewProductName.text = product.nameShort
            vh.itemView.textViewPrice.text =
                vh.itemView.resources.getString(
                    R.string.price,
                    product.productPrice.price.toString()
                )

            product.imageUris.firstOrNull()?.let { url ->
                Glide
                    .with(vh.itemView)
                    .load(
                        vh.itemView.resources.getString(
                            R.string.imageUrl,
                            url
                        )
                    )
                    .placeholder(R.drawable.loading)
                    .into(vh.itemView.imageViewProduct)
            }
            vh.itemView.setOnClickListener { onSelected(product) }
            vh.itemView.buttonAddRemove.setOnClickListener {
                addToCart(product)
            }
        }
    }

    companion object {
        val ProductDiffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.sku == newItem.sku
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}