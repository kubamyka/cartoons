package com.kmcoding.cartoons

import com.kmcoding.cartoons.data.repository.FakeCartoonRepositoryImpl
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.data.source.FakeDataSource.getFakeCartoonsWithQuerySize
import com.kmcoding.cartoons.util.MainDispatcherRule
import com.kmcoding.cartoons.view.screens.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

  private lateinit var homeViewModel: HomeViewModel

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Before
  fun setup() {
    homeViewModel = HomeViewModel(cartoonRepository = FakeCartoonRepositoryImpl())
  }

  @Test
  fun `verify cartoons list size after load`() = runTest {
    backgroundScope.launch(mainDispatcherRule.testDispatcher) {
      homeViewModel.cartoons.collect()
    }
    assertEquals(fakeCartoons.size, homeViewModel.cartoons.value.size)
  }

  @Test
  fun `verify if search is active after toggle`() = runTest {
    homeViewModel.toggleSearchActive()

    assertTrue(homeViewModel.isSearchActive.value)
    assertEquals("", homeViewModel.searchQuery.value)
  }

  private fun `verify cartoons list size after entered title query`(query: String) {
    homeViewModel.updateQuery(query)
    assertEquals(getFakeCartoonsWithQuerySize(query), homeViewModel.cartoons.value.size)
  }

  @Test
  fun `verify if cartoons list is empty after incorrectly entered title query`() = runTest {
    backgroundScope.launch(mainDispatcherRule.testDispatcher) {
      homeViewModel.cartoons.collect()
    }
    `verify cartoons list size after entered title query`("Wrong title")
  }

  @Test
  fun `verify cartoons list size after correctly entered title query`() = runTest {
    backgroundScope.launch(mainDispatcherRule.testDispatcher) {
      homeViewModel.cartoons.collect()
    }
    `verify cartoons list size after entered title query`("Fake cartoon 2")
  }
}