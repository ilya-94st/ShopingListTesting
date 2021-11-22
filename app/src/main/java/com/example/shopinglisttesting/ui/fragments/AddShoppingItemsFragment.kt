package com.example.shopinglisttesting.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.shopinglisttesting.R
import com.example.shopinglisttesting.base.BaseFragment
import com.example.shopinglisttesting.databinding.FragmentAddShoppingItemsBinding
import com.example.shopinglisttesting.other.Resources
import javax.inject.Inject

class AddShoppingItemsFragment @Inject constructor(val glide: RequestManager) : BaseFragment<FragmentAddShoppingItemsBinding>() {
    lateinit var viewModel: ShoppingViewModel

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddShoppingItemsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeImages()

        binding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItems(binding.etShoppingItemName.text.toString(),
            binding.etShoppingItemAmount.text.toString(),
            binding.etShoppingItemPrice.text.toString())
        }

        binding.ivShoppingImage.setOnClickListener {
            findNavController().navigate(R.id.action_addShoppingItemsFragment_to_imageFragment)
        }
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
             viewModel.setImagesUrl("")
             findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun subscribeImages() {
        viewModel.imagesUrl().observe(viewLifecycleOwner, Observer {
            images->
            glide.load(images).into(binding.ivShoppingImage)
        })
        viewModel.insertShoppingStatus().observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result->
                when(result.status){
                    Resources.Status.SUCCESSES -> {
                      snackBar("Added Shopping item")
                        findNavController().popBackStack()
                    }
                    Resources.Status.ERROR -> {
                       snackBar(result.message?: "unknown error")
                    }
                    Resources.Status.LOADING -> {

                    }
                }
            }
        })
    }
}