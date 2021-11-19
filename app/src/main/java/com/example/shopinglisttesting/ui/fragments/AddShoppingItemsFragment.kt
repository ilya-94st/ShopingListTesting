package com.example.shopinglisttesting.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.shopinglisttesting.base.BaseFragment
import com.example.shopinglisttesting.databinding.FragmentAddShoppingItemsBinding

class AddShoppingItemsFragment : BaseFragment<FragmentAddShoppingItemsBinding>() {
    private lateinit var viewModel: ShoppingViewModel

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddShoppingItemsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

    }
}