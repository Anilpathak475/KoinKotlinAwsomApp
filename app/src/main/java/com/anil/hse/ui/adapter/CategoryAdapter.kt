package com.anil.hse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anil.hse.R
import com.anil.hse.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    val onSelected: (category: Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CartViewHolder>() {
    var categories = listOf<Category>()
    fun setCategoriesData(
        categories: List<Category>
    ) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false))
    }

    override fun onBindViewHolder(vh: CartViewHolder, position: Int) {
        val category = categories[position]
        vh.itemView.textViewCategoryName.text = category.displayName
        vh.itemView.setOnClickListener { onSelected(category) }

    }

    override fun getItemCount() = categories.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}