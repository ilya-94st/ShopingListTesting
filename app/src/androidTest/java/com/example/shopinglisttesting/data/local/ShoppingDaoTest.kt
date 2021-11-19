package com.example.shopinglisttesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shopinglisttesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest // определение нагрузки тестов
class ShoppingDaoTest {



    @Inject
    // необходим нейминг для того чтобы хилт распознал какую внедрять базу данных
    @Named("test_db")
    lateinit var shoppingDatabase: ShoppingDatabase
    private lateinit var daoShopping: DaoShopping

    // пишем правило для хилтеста
    @get:Rule
    var hiltAndroidTest = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() // это правило нужно чтобы наше jvm не выдовал ошибкт из-за livedata

    @Before
    fun setup() {
        // внедряем нашу тестовую базу
         hiltAndroidTest.inject()
        daoShopping = shoppingDatabase.getDaoShopping()
    }

    @After
    fun teardown() {
        shoppingDatabase.close()
    }


    @Test
    fun insertShopping() = runBlockingTest {
        val shoppingItems = ShoppingItems("banana", 1, 1f, "url", 1) // создаем фейковую базу данных
        daoShopping.insert(shoppingItems) // помещаем ее

        val allShoppingItems = daoShopping.readAll().getOrAwaitValue() // считываем базу данных
        assertThat(allShoppingItems).contains(shoppingItems) // проверка содержит ли наш allShoppingItems shoppingItems
    }

    @Test
    fun deleteShopping() = runBlockingTest {
        val shoppingItems = ShoppingItems("banana", 1, 1f, "url", 1) // создаем фейковую базу данных
        daoShopping.insert(shoppingItems) // помещаем ее
        daoShopping.deleteShoppingItems(shoppingItems)

        val allShoppingItems = daoShopping.readAll().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(shoppingItems)
    }

    @Test
    fun readTotalPriseSum() = runBlockingTest {
        val shoppingItems1 = ShoppingItems("banana1", 1, 1f, "url", 1)
        val shoppingItems2 = ShoppingItems("banana2", 2, 4f, "url2", 2)
        val shoppingItems3 = ShoppingItems("banana3", 0, 6f, "url3", 3)
        daoShopping.insert(shoppingItems1)
        daoShopping.insert(shoppingItems2)
        daoShopping.insert(shoppingItems3)

        val totalShoppingPrice = daoShopping.readTotalPrise().getOrAwaitValue()

        assertThat(totalShoppingPrice).isEqualTo(1 + 2*4)
    }
}