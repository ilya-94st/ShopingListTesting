package com.example.shopinglisttesting.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglisttesting.R
import com.example.shopinglisttesting.adapters.ShoppingAdapter
import com.example.shopinglisttesting.base.BaseFragment
import com.example.shopinglisttesting.databinding.FragmentShopingBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class FragmentShopping @Inject constructor(val shoppingAdapter: ShoppingAdapter, var viewModel: ShoppingViewModel? = null): BaseFragment<FragmentShopingBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShopingBinding.inflate(inflater,container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(R.id.action_frragmentShoping_to_addShoppingItemsFragment)
        }
        initAdapter()
        subscribeObserves()
    }

    val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
        , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        @SuppressLint("ShowToast")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = shoppingAdapter.shoppingItems[position]
            viewModel?.deleteShoppingItems(item)
            Snackbar.make(requireView(), "item successful delete", Snackbar.LENGTH_LONG).apply {
                setAction("Undo"){
                    viewModel?.insertShoppingItemsDB(item)
                }
                show()
            }
        }
    }

    private fun subscribeObserves() {
      viewModel?.readShoppingItems()?.observe(viewLifecycleOwner, Observer {
          shoppingAdapter.shoppingItems = it
      })

        viewModel?.totalPrise()?.observe(viewLifecycleOwner, Observer {
            val price = it?: 0f
            val priceText = "total price${price}"
            binding.tvShoppingItemPrice.text = priceText
        })
    }

    private fun initAdapter() {
        binding.rvShoppingItems.adapter = shoppingAdapter
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvShoppingItems)
        }
    }
}