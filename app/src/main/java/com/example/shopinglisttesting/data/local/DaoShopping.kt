package com.example.shopinglisttesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoShopping {

    @Query("select * from shopping_items")
    fun readAll() : LiveData<List<ShoppingItems>> // живые данные смотрят за изменения в базе данных

    @Insert(onConflict = OnConflictStrategy.REPLACE) // нужно если в базе данных мы хотим добавить схожий элемент то он просто его заменит
    suspend fun insert(shoppingItems: ShoppingItems)

    @Delete
    suspend fun deleteShoppingItems(shoppingItems: ShoppingItems)

    @Query("select sum(prise * amount) from shopping_items")
    fun readTotalPrise() : LiveData<Float>
}