package com.example.shopinglisttesting.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.shopinglisttesting.adapters.ImageAdapter
import com.example.shopinglisttesting.adapters.ShoppingAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingAdapter: ShoppingAdapter
):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImageFragment::class.java.name -> {
                ImageFragment(imageAdapter)
            }
            AddShoppingItemsFragment::class.java.name -> {
                AddShoppingItemsFragment(glide)
            }
            FragmentShopping::class.java.name -> {
                FragmentShopping(shoppingAdapter)
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}