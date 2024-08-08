package com.kmcoding.cartoons.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.view.screens.detail.CartoonDetailsScreen
import com.kmcoding.cartoons.view.screens.detail.CartoonDetailsViewModel
import com.kmcoding.cartoons.view.screens.list.CartoonsListScreenNav
import com.kmcoding.cartoons.view.screens.list.CartoonsListViewModel
import com.kmcoding.cartoons.view.screens.list.CartoonsScreen

@Composable
fun MainContent() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = CartoonsListScreenNav) {
    composable<CartoonsListScreenNav> {
      val cartoonsListViewModel: CartoonsListViewModel = hiltViewModel()
      CartoonsScreen(viewModel = cartoonsListViewModel, modifier = Modifier.fillMaxSize(),
        navController = navController)
    }

    composable<Cartoon> { backStackEntry ->
      val cartoonDetailsViewModel: CartoonDetailsViewModel = hiltViewModel()
      val cartoon by cartoonDetailsViewModel.cartoon.collectAsStateWithLifecycle()
      CartoonDetailsScreen(cartoon = cartoon, navigateBack = { navController.navigateUp() })
    }
  }
}