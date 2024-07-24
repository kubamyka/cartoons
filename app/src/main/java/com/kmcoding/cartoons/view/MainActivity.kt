package com.kmcoding.cartoons.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import com.kmcoding.cartoons.view.theme.CartoonsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @OptIn(ExperimentalMaterialApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CartoonsTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = CartoonsListScreenNav) {
          composable<CartoonsListScreenNav> {
            val cartoonsListViewModel: CartoonsListViewModel = hiltViewModel()
            val cartoons by cartoonsListViewModel.cartoons.collectAsStateWithLifecycle()
            val isSearchActive by cartoonsListViewModel.isSearchActive.collectAsStateWithLifecycle()
            val isLoading by cartoonsListViewModel.isLoading.collectAsStateWithLifecycle()
            val query by cartoonsListViewModel.searchQuery.collectAsStateWithLifecycle()
            val pullRefreshState = rememberPullRefreshState(refreshing = isLoading,
              onRefresh = cartoonsListViewModel::fetchCartoons)

            CartoonsScreen(cartoons = cartoons, query = query,
              onQueryChange = { cartoonsListViewModel.updateQuery(it) },
              isSearchActive = isSearchActive,
              isLoading = isLoading,
              pullRefreshState = pullRefreshState,
              toggleSearchActive = { cartoonsListViewModel.toggleSearchActive() },
              navigateToDetails = { cartoon ->
                navController.navigate(route = cartoon)
              }, modifier = Modifier.fillMaxSize())
          }

          composable<Cartoon> { backStackEntry ->
            val cartoonDetailsViewModel: CartoonDetailsViewModel = hiltViewModel()
            val cartoon by cartoonDetailsViewModel.cartoon.collectAsStateWithLifecycle()
            CartoonDetailsScreen(cartoon = cartoon, navigateBack = { navController.navigateUp() })
          }
        }
      }
    }
  }
}