package com.example.shopinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shopinglisttesting.MainCoroutineRule
import com.example.shopinglisttesting.getOrAwaitValueTest
import com.example.shopinglisttesting.other.Constants
import com.example.shopinglisttesting.other.Resources
import com.example.shopinglisttesting.repositories.FakeShoppingRepository
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    // если поле количество пусото
    fun `insert shopping item with empty field return error`() {
        viewModel.insertShoppingItems("name", "", "7.0")

        val value = viewModel.insertShoppingStatus().getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resources.Status.ERROR) // getContentIfNotHandled() функция которая возращает значение ресурса. Наш статус
    }

    @Test
    // если поле имя слишком велико
    fun `insert shopping item with too long name return error`() {
        // тестируем максимальную длину строки
        val string = buildString {
            for(i in 1..Constants.MAX_LENGTH_NAME + 1) {
                append(i)
            }
        }
        viewModel.insertShoppingItems(string, "12", "7.0")

        val value = viewModel.insertShoppingStatus().getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resources.Status.ERROR)
    }

    @Test
    // если поле price слишком велико
    fun `insert shopping item with too long price return error`() {
        // тестируем максимальную длину строки
        val string = buildString {
            for(i in 1..Constants.MAX_LENGTH_PRICE + 1) {
                append(i)
            }
        }
        viewModel.insertShoppingItems("name", "12", string)

        val value = viewModel.insertShoppingStatus().getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resources.Status.ERROR)
    }

    @Test
    // если поле amount слишком велико
    fun `insert shopping item with too long amount return error`() {
        viewModel.insertShoppingItems("name", "1211111111111111111111111", "7.0")

        val value = viewModel.insertShoppingStatus().getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resources.Status.ERROR)
    }

    @Test
    // если все поля введены коректно
    fun `insert shopping item with valid input amount return SUCCESSES`() {
        viewModel.insertShoppingItems("name", "12", "7.0")

        val value = viewModel.insertShoppingStatus().getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Resources.Status.SUCCESSES)
    }
}