package com.kmcoding.cartoons

import com.kmcoding.cartoons.view.screens.list.CartoonsListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CartoonsListViewModelTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var cartoonsListViewModel: CartoonsListViewModel

  @Before
  fun init() {
    hiltRule.inject()
  }

  @Test
  fun `asd`() {
    assertEquals("", "")
  }
}