package com.example.shopinglisttesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopinglisttesting.adapters.ImageAdapter
import com.example.shopinglisttesting.base.BaseFragment
import com.example.shopinglisttesting.databinding.FragmentImageBinding
import com.example.shopinglisttesting.other.Constants
import javax.inject.Inject

class ImageFragment @Inject constructor(
   val imageAdapter: ImageAdapter
) : BaseFragment<FragmentImageBinding>() {
    lateinit var viewModel: ShoppingViewModel

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentImageBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        initAdapter()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        binding.rvImages.adapter = imageAdapter
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), Constants.GRID_SPAN_COUNT)
    }
}