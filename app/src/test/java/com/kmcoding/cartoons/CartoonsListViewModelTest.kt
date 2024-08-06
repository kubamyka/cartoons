package com.kmcoding.cartoons

import com.kmcoding.cartoons.data.repository.FakeCartoonRepositoryImpl
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.extension.MainDispatcherRule
import com.kmcoding.cartoons.view.screens.list.CartoonsListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
  fun `verify cartoons items size after load`() {
    runBlocking {
      assertEquals(fakeCartoons.size, cartoonsListViewModel.cartoons.value.size)
    }
  }
}