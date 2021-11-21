package com.example.shopinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.shopinglisttesting.R
import com.example.shopinglisttesting.adapters.ImageAdapter
import com.example.shopinglisttesting.getOrAwaitValue
import com.example.shopinglisttesting.launchFragmentInHiltContainer
import com.example.shopinglisttesting.repositories.FakeShoppingRepositoryAndroidTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImageFragmentTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var factory: ShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAddImageUrl() {
        val imageTest = "ImageTest"
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ImageFragment>(fragmentFactory = factory) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf(imageTest)
            viewModel = testViewModel
            // adb shell settings put global animator_duration_scale 0 чтобы отключить анимацию иначе наш тест сработает не корректно
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.imagesUrl().getOrAwaitValue()).isEqualTo(imageTest)
    }
}