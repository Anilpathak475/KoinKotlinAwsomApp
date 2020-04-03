package com.anil.hse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anil.hse.R
import com.anil.hse.model.Product
import com.anil.hse.ui.adapter.ProductAdapter
import com.anil.hse.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_products.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {
    private val productsViewModel: ProductsViewModel by viewModel()

    private val navigation by lazy { findNavController() }

    private val adapter by lazy {
        ProductAdapter(
            { onProductDetail(it) },
            { onProductAddToCart(it) })
    }

    private val categoryId by lazy {
        arguments?.let { ProductsFragmentArgs.fromBundle(it).categoryId }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_products, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId?.let { productsViewModel.setCategory(it) }
        observeData()

        recyclerviewProducts.apply {
            adapter = this@ProductsFragment.adapter
            layoutManager = LinearLayoutManager(this@ProductsFragment.context)
        }

        layoutCart.setOnClickListener { navigation.navigate(ProductsFragmentDirections.actionProductsFragmentToCartFragment()) }
    }

    private fun observeData() {
        productsViewModel.products.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        productsViewModel.cart.observe(viewLifecycleOwner, Observer {
            val quantity = it.map { cartEntity -> cartEntity.quantity }.sum()
            if (quantity > 0) {
                layoutCart.visibility = View.VISIBLE
                textViewCartItems.text = quantity.toString()
            } else {
                layoutCart.visibility = View.GONE
            }
        })

        productsViewModel.cartNotification.observe(viewLifecycleOwner, Observer {
            showError(it)
        })
    }

    private fun showError(error: String) =
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()

    private fun onProductDetail(product: Product) {
        val directions =
            ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(
                product.sku
            )
        navigation.navigate(directions)
    }

    private fun onProductAddToCart(product: Product) =
        productsViewModel.addItemInCart(product, 1) {
            showError(it)
        }

}
