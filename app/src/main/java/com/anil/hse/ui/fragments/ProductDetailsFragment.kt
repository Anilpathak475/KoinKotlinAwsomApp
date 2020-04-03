package com.anil.hse.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anil.hse.R
import com.anil.hse.base.gone
import com.anil.hse.base.visible
import com.anil.hse.model.Product
import com.anil.hse.networking.Resource
import com.anil.hse.networking.Status
import com.anil.hse.ui.adapter.ProductImageAdapter
import com.anil.hse.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_product_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsFragment : Fragment() {

    private val productsViewModel: ProductsViewModel by viewModel()

    private val productId by lazy {
        arguments?.let { ProductDetailsFragmentArgs.fromBundle(it).productId }
    }

    private val imageAdapter by lazy {
        ProductImageAdapter()
    }

    private val observer = Observer<Resource<Product>> {
        when (it.status) {
            Status.SUCCESS -> it?.let {
                it.data?.let { product ->
                    productName.text = product.nameShort
                    textViewCategory.text = product.brandNameLong
                    textViewPrice.text =
                        getString(R.string.price, product.productPrice.price.toString())

                    textViewDescription.text =
                        product.longDescription
                    imageAdapter.setImageData(product.imageUris)
                    buttProductDetailAddToCart.setOnClickListener {
                        productsViewModel.addItemInCart(
                            product,
                            1
                        ) { status -> showError(status) }
                    }
                }
                layoutData.visible()
                loading.gone()
            }
            Status.ERROR -> it.message?.let { error -> showError(error) }
            Status.LOADING -> showLoading()
        }
    }

    private fun showError(error: String) =
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()

    private fun showLoading() {
        layoutData.gone()
        loading.visible()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_product_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewImages.apply {
            adapter = this@ProductDetailsFragment.imageAdapter
            layoutManager = LinearLayoutManager(
                this@ProductDetailsFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        productsViewModel.product.observe(viewLifecycleOwner, observer)
        productsViewModel.cart.observe(viewLifecycleOwner, Observer {
            it.find { cart -> cart.productId == productId }?.let {
                buttProductDetailAddToCart.visibility = View.GONE
            }
        })

        productsViewModel.cartNotification.observe(viewLifecycleOwner, Observer {
            showError(it)
        })
        productId?.let {
            productsViewModel.fetchProductProductDetail(it)
        }
    }
}
