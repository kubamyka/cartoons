package com.kmcoding.cartoons

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.kmcoding.cartoons.data.repository.FakeCartoonRepositoryImpl
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdContentDescription
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdTag
import com.kmcoding.cartoons.util.CartoonsTestHelper.onNodeWithStringIdText
import com.kmcoding.cartoons.view.MainActivity
import com.kmcoding.cartoons.view.screens.CartoonsViewModel
import com.kmcoding.cartoons.view.screens.list.CartoonsListPane
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class CartoonScreenTest {

  private lateinit var cartoonsListViewModel: CartoonsViewModel

  @get:Rule(order = 1)
  var hiltTestRule = HiltAndroidRule(this)

  @get:Rule(order = 2)
  var composeTestRule = createAndroidComposeRule<MainActivity>()

  @Before
  fun setup() {
    hiltTestRule.inject()
    cartoonsListViewModel = CartoonsViewModel(cartoonRepository = FakeCartoonRepositoryImpl())
    composeTestRule.activity.setContent {
      //CartoonsListPane(viewModel = cartoonsListViewModel)
    }
  }

  @Test
  fun verifyThatCartoonsListIsDisplayedAfterLoad() {
    composeTestRule.onNodeWithStringIdTag(R.string.tag_cartoons_list).assertIsDisplayed()
  }

  @Test
  fun verifyThatSearchIsActiveAfterSearchIconClick() {
    composeTestRule.onNodeWithStringIdContentDescription(R.string.search).performClick()
    composeTestRule.onNodeWithStringIdText(R.string.enter_phrase_here).assertIsDisplayed()
  }

  @Test
  fun verifyThatSearchIsInactiveAfterCloseIconClick() {
    composeTestRule.onNodeWithStringIdContentDescription(R.string.search).performClick()
    composeTestRule.onNodeWithStringIdContentDescription(R.string.close).performClick()
    composeTestRule.onNodeWithStringIdTag(R.string.tag_top_bar)
      .assertTextEquals(composeTestRule.activity.getString(R.string.app_name))
  }

  @Test
  fun verifyThatCartoonsListIsEmptyAfterIncorrectlyEnteredTitleQuery() {
    composeTestRule.onNodeWithStringIdContentDescription(R.string.search).performClick()
    composeTestRule.onNodeWithStringIdTag(R.string.tag_search_text_field)
      .performTextInput("Wrong title")
    composeTestRule.onNodeWithStringIdText(R.string.empty_list).assertIsDisplayed()
  }

  @Test
  fun verifyThatCartoonItemIsDisplayedAfterCorrectlyEnteredTitleQuery() {
    val query = "Fake cartoon 2"
    composeTestRule.onNodeWithStringIdContentDescription(R.string.search).performClick()
    composeTestRule.onNodeWithStringIdTag(R.string.tag_search_text_field).performTextInput(query)
    composeTestRule.onNodeWithStringIdTag(R.string.tag_cartoons_list)
      .performScrollToNode(hasText(query)).assertIsDisplayed()
  }

}