package com.example.shopinglisttesting.di

import android.content.Context
import androidx.room.Room
import com.example.shopinglisttesting.data.local.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun providesInMemoryDb(@ApplicationContext app: Context) =
        Room.inMemoryDatabaseBuilder(app, ShoppingDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}