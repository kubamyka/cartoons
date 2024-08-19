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
import com.kmcoding.cartoons.view.CartoonsAppContent
import com.kmcoding.cartoons.view.MainActivity
import com.kmcoding.cartoons.view.screens.home.HomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeScreenTest {
    private lateinit var homeViewModel: HomeViewModel

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
        homeViewModel = HomeViewModel(cartoonRepository = FakeCartoonRepositoryImpl())
        composeTestRule.activity.setContent {
            CartoonsAppContent(viewModel = homeViewModel)
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
            .assertTextEquals(composeTestRule.activity.getString(R.string.search_cartoons))
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

    @Test
    fun verifyThatCartoonDetailsAreDisplayedAfterLoad() {
        // TODO
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
