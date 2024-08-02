package com.kmcoding.cartoons

import androidx.activity.viewModels
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.kmcoding.cartoons.view.MainActivity
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

  @get:Rule(order = 1)
  var hiltTestRule = HiltAndroidRule(this)

  @get:Rule(order = 2)
  var composeTestRule = createAndroidComposeRule<MainActivity>()

  @Before
  fun setup() {
    hiltTestRule.inject()
    composeTestRule.setContent {
      CartoonsScreen(viewModel = composeTestRule.activity.viewModels<CartoonsListViewModel>().value)
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