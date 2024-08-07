package com.kmcoding.cartoons

import com.kmcoding.cartoons.data.repository.FakeCartoonRepositoryImpl
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.data.source.FakeDataSource.getFakeCartoonsWithQuerySize
import com.kmcoding.cartoons.extension.MainDispatcherRule
import com.kmcoding.cartoons.view.screens.list.CartoonsListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CartoonsListViewModelTest {

  private lateinit var cartoonsListViewModel: CartoonsListViewModel

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Before
  fun setup() {
    cartoonsListViewModel = CartoonsListViewModel(cartoonRepository = FakeCartoonRepositoryImpl())
  }

  @Test
  fun `verify cartoons list size after load`() = runTest {
    backgroundScope.launch(mainDispatcherRule.testDispatcher) {
      cartoonsListViewModel.cartoons.collect()
    }
    assertEquals(fakeCartoons.size, cartoonsListViewModel.cartoons.value.size)
  }

  @Test
  fun `verify if search is active after toggle`() = runTest {
    cartoonsListViewModel.toggleSearchActive()

    assertTrue(cartoonsListViewModel.isSearchActive.value)
    assertEquals("", cartoonsListViewModel.searchQuery.value)
  }

  private fun `verify cartoons list size after entered title query`(query: String) {
    cartoonsListViewModel.updateQuery(query)
    assertEquals(getFakeCartoonsWithQuerySize(query), cartoonsListViewModel.cartoons.value.size)
  }

  @Test
  fun `verify if cartoons list is empty after incorrectly entered title query`() = runTest {
    backgroundScope.launch(mainDispatcherRule.testDispatcher) {
      cartoonsListViewModel.cartoons.collect()
    }
    `verify cartoons list size after entered title query`("Wrong title")
  }

  @Test
  fun `verify cartoons list size after correctly entered title query`() = runTest {
    backgroundScope.launch(mainDispatcherRule.testDispatcher) {
      cartoonsListViewModel.cartoons.collect()
    }
    `verify cartoons list size after entered title query`("Fake cartoon 2")
  }
}