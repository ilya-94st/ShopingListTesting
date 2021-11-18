package com.example.shopinglisttesting.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ResponseImage
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.other.Resources
import com.example.shopinglisttesting.repository.ShoppingRepository

class FaceShoppingRepository: ShoppingRepository {

    private val shoppingItemsFake = mutableListOf<ShoppingItems>()

    private val readAllShoppingItems = MutableLiveData<List<ShoppingItems>>()

    private val readTotalSumPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    private fun refreshShoppingItems() {
       readAllShoppingItems.postValue(shoppingItemsFake)
        readTotalSumPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItemsFake.sumByDouble { it.prise.toDouble() }.toFloat()
    }

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun insert(shoppingItems: ShoppingItems) {
        shoppingItemsFake.add(shoppingItems)
        refreshShoppingItems()
    }

    override suspend fun deleteShoppingItems(shoppingItems: ShoppingItems) {
        shoppingItemsFake.remove(shoppingItems)
        refreshShoppingItems()
    }

    override fun readAll(): LiveData<List<ShoppingItems>> = readAllShoppingItems

    override fun readTotalPrise(): LiveData<Float> = readTotalSumPrice

    override suspend fun searchPixImage(pixImage: String): Resources<ResponseImage> {
       return if(shouldReturnNetworkError) {
           Resources.error("Error", null)
       } else {
           Resources.successes(ResponseImage(listOf(), 0, 0))
       }
    }
}