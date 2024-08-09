package com.kmcoding.cartoons

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.kmcoding.cartoons.data.repository.FakeCartoonRepositoryImpl
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdContentDescription
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdTag
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdText
import com.kmcoding.cartoons.view.MainActivity
import com.kmcoding.cartoons.view.MainContent
import com.kmcoding.cartoons.view.screens.detail.CartoonDetailsScreen
import com.kmcoding.cartoons.view.screens.list.CartoonsListViewModel
import com.kmcoding.cartoons.view.screens.list.CartoonsScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
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
      CartoonDetailsScreen(fakeCartoons[0])
    }
  }

  @Test
  fun verifyThatCartoonDetailsAreDisplayedAfterLoad() {
    val cartoon = fakeCartoons[0]
    composeTestRule.onNodeWithStringIdTag(R.string.tag_top_bar).assertTextEquals(cartoon.title)
    composeTestRule.onNodeWithStringIdText(R.string.creation_year).assertIsDisplayed()
    composeTestRule.onNodeWithText(cartoon.year.toString()).assertIsDisplayed()
    composeTestRule.onNodeWithStringIdText(R.string.episodes).assertIsDisplayed()
    composeTestRule.onNodeWithText(cartoon.episodes.toString()).assertIsDisplayed()
    composeTestRule.onNodeWithStringIdText(R.string.episode_duration).assertIsDisplayed()
    composeTestRule.onNodeWithText(cartoon.episodeDuration.toString()).assertIsDisplayed()
    composeTestRule.onNodeWithStringIdText(R.string.creator).assertIsDisplayed()
    composeTestRule.onNodeWithText(cartoon.creators[0]).assertIsDisplayed()
    composeTestRule.onNodeWithStringIdText(R.string.genre).assertIsDisplayed()
    composeTestRule.onNodeWithText(cartoon.genres[0]).assertIsDisplayed()
  }


}