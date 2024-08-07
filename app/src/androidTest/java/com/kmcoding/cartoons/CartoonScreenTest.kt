package com.kmcoding.cartoons

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.kmcoding.cartoons.data.repository.FakeCartoonRepositoryImpl
import com.kmcoding.cartoons.view.screens.list.CartoonsListViewModel
import com.kmcoding.cartoons.view.screens.list.CartoonsScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class CartoonScreenTest {

  private lateinit var cartoonsListViewModel: CartoonsListViewModel

  @get:Rule(order = 1)
  var hiltTestRule = HiltAndroidRule(this)

  @get:Rule(order = 2)
  var composeTestRule = createComposeRule()

  @Before
  fun setup() {
    hiltTestRule.inject()
    cartoonsListViewModel = CartoonsListViewModel(cartoonRepository = FakeCartoonRepositoryImpl())
    composeTestRule.setContent {
      CartoonsScreen(viewModel = cartoonsListViewModel)
    }
  }

  @Test
  fun app_displays_list_of_items() {
    //assert the list is displayed
    composeTestRule.onNodeWithText("Cartoons").assertIsDisplayed()

    //assert all items exist within the tree
    /*dummyItems.forEach { item ->
      composeTestRule.onNodeWithText(item.name).assertExists()
    }*/
  }
}