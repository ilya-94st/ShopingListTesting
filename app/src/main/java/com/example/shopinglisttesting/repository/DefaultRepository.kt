package com.example.shopinglisttesting.repository

import androidx.lifecycle.LiveData
import com.ResponseImage
import com.example.shopinglisttesting.api.PixAbayApi
import com.example.shopinglisttesting.data.local.DaoShopping
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.other.Resources
import retrofit2.Response
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    val shopping: DaoShopping,
    val pixAbayApi: PixAbayApi
): ShoppingRepository {
    override suspend fun insert(shoppingItems: ShoppingItems) = shopping.insert(shoppingItems)

    override suspend fun deleteShoppingItems(shoppingItems: ShoppingItems) = shopping.deleteShoppingItems(shoppingItems)

    override fun readAll(): LiveData<List<ShoppingItems>> = shopping.readAll()

    override fun readTotalPrise(): LiveData<Float> = shopping.readTotalPrise()

    override suspend fun searchPixImage(pixImage: String): Resources<ResponseImage> {
        return try {
            val response = pixAbayApi.getPixImage(pixImage)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resources.successes(it)
                } ?: Resources.error("An unknown error occured", null)
            } else {
                Resources.error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Resources.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}