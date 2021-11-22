package com.example.shopinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.example.shopinglisttesting.R
import com.example.shopinglisttesting.adapters.ShoppingAdapter
import com.example.shopinglisttesting.data.local.ShoppingItems
import com.example.shopinglisttesting.getOrAwaitValue
import com.example.shopinglisttesting.launchFragmentInHiltContainer
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
class FragmentShoppingTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testShoppingFragmentFactory: TestShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun swipeItem_deleteDB() {
        val shoppingItems = ShoppingItems("Test", 32, 12.2f, "TestUrl", 1)
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<FragmentShopping>(fragmentFactory = testShoppingFragmentFactory) {
            testViewModel = viewModel
            viewModel?.insertShoppingItemsDB(shoppingItems)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingAdapter.ShoppingViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.readShoppingItems()?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<FragmentShopping> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(navController).navigate(R.id.action_frragmentShoping_to_addShoppingItemsFragment)
    }
}