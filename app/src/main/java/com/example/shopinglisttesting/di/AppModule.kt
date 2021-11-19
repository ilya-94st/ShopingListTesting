package com.example.shopinglisttesting.di

import android.content.Context
import androidx.room.Room
import com.example.shopinglisttesting.api.PixAbayApi
import com.example.shopinglisttesting.data.local.DaoShopping
import com.example.shopinglisttesting.data.local.ShoppingDatabase
import com.example.shopinglisttesting.other.Constants
import com.example.shopinglisttesting.other.Constants.DB_NAME
import com.example.shopinglisttesting.repository.DefaultRepository
import com.example.shopinglisttesting.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePixImageApi(): PixAbayApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PixAbayApi::class.java)


    @Provides
    @Singleton
    fun provideShoppingDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, ShoppingDatabase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun getRunningDao(db: ShoppingDatabase) = db.getDaoShopping()

    @Provides
    @Singleton
    fun provideDefaultShoppingRepositories(daoShopping: DaoShopping, api: PixAbayApi,): ShoppingRepository = DefaultRepository(daoShopping, api)
}