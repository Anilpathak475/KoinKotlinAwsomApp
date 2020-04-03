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
import com.anil.hse.model.Category
import com.anil.hse.networking.Resource
import com.anil.hse.networking.Status
import com.anil.hse.ui.adapter.CategoryAdapter
import com.anil.hse.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_categories.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {
    private val categoryViewModel: CategoryViewModel by viewModel()
    private val adapter by lazy {
        CategoryAdapter { onCategorySelected(it.categoryId.toString()) }
    }

    private val observer by lazy {
        Observer<Resource<Category>> {
            when (it.status) {
                Status.SUCCESS -> it?.let {
                    it.data?.children?.let { categories ->
                        adapter.setCategoriesData(categories)
                    }
                }
                Status.ERROR -> it.message?.let { error -> showError(error) }
                Status.LOADING -> showLoading()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_categories, container, false)

    private fun showError(error: String) =
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()

    private fun showLoading() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel.categories.observe(viewLifecycleOwner, observer)
        recyclerviewCategories.apply {
            adapter = this@CategoriesFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        categoryViewModel.fetchCategories()
    }

    private fun onCategorySelected(categoryId: String) {
        val directions =
            CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(
                categoryId
            )
        findNavController().navigate(directions)
    }
}
