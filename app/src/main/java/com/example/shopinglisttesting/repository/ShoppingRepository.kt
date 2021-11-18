package com.example.shopinglisttesting.repository

import androidx.lifecycle.LiveData
import com.ResponseImage
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.other.Resources
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insert(shoppingItems: ShoppingItems)

    suspend fun deleteShoppingItems(shoppingItems: ShoppingItems)

    fun readAll() : LiveData<List<ShoppingItems>>

    fun readTotalPrise() : LiveData<Float>

    suspend fun searchPixImage(pixImage: String): Resources<ResponseImage>
}