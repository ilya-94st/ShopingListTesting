package com.example.shopinglisttesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shopinglisttesting.R
import com.example.shopinglisttesting.base.BaseFragment
import com.example.shopinglisttesting.databinding.FragmentShopingBinding


class FragmentShopping : BaseFragment<FragmentShopingBinding>() {
    private lateinit var viewModel: ShoppingViewModel
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShopingBinding.inflate(inflater,container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(R.id.action_frragmentShoping_to_addShoppingItemsFragment)
        }
    }
}