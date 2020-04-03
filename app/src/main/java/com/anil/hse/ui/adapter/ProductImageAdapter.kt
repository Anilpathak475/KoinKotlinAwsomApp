package com.anil.hse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anil.hse.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_image.view.*

class ProductImageAdapter : RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>() {
    var urls = listOf<String>()
    fun setImageData(urls: List<String>) {
        this.urls = urls
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductImageViewHolder(
            layoutInflater.inflate(
                R.layout.item_product_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(vh: ProductImageViewHolder, position: Int) {
        val url = urls[position]
        Glide
            .with(vh.itemView)
            .load(
                vh.itemView.resources.getString(
                    R.string.imageUrl,
                    url
                )
            )
            .centerCrop()
            .placeholder(R.drawable.loading)
            .into(vh.itemView.imageViewItemProduct)
    }

    override fun getItemCount() = urls.size

    class ProductImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}