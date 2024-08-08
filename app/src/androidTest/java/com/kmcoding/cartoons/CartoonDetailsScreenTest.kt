package com.kmcoding.cartoons

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.kmcoding.cartoons.data.repository.FakeCartoonRepositoryImpl
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdContentDescription
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdTag
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdText
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
class CartoonDetailsScreenTest {

  private lateinit var cartoonsListViewModel: CartoonsListViewModel

  @get:Rule(order = 1)
  var hiltTestRule = HiltAndroidRule(this)

  @get:Rule(order = 2)
  var composeTestRule = createAndroidComposeRule<MainActivity>()

  @Before
  fun setup() {
    hiltTestRule.inject()
    cartoonsListViewModel = CartoonsListViewModel(cartoonRepository = FakeCartoonRepositoryImpl())
    composeTestRule.activity.setContent {
      CartoonsScreen(viewModel = cartoonsListViewModel)
    }
  }
}