package com.example.shopinglisttesting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItems::class],
    version = 1, exportSchema = false
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun getDaoShopping(): DaoShopping

}