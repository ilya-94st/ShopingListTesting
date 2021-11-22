package com.example.shopinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.bumptech.glide.RequestManager
import com.google.common.truth.Truth.assertThat
import com.example.shopinglisttesting.R
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.getOrAwaitValue
import com.example.shopinglisttesting.launchFragmentInHiltContainer
import com.example.shopinglisttesting.repositories.FakeShoppingRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemsFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var factory: ShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun insertShoppingDb() {
        val viewModelTest = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemsFragment>(fragmentFactory = factory) {
            viewModel = viewModelTest
        }
        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("55"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))

        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(viewModelTest.readShoppingItems().getOrAwaitValue()).contains(ShoppingItems("shopping item",
        55, 5.5f,""))
    }

    @Test
    fun pressBackButton_popBackState() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun clickAddShoppingFragment_navigateToImageFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(R.id.action_addShoppingItemsFragment_to_imageFragment)
    }
}