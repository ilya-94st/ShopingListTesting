package com.example.shopinglisttesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopinglisttesting.base.BaseFragment
import com.example.shopinglisttesting.databinding.FragmentShopingBinding


class FragmentShopping : BaseFragment<FragmentShopingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShopingBinding.inflate(inflater,container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}