package com.kmcoding.cartoons.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kmcoding.cartoons.view.screens.detail.CartoonDetailScreen
import com.kmcoding.cartoons.view.screens.detail.CartoonDetailScreenNav
import com.kmcoding.cartoons.view.screens.detail.CartoonDetailScreenNav2
import com.kmcoding.cartoons.view.screens.list.CartoonsListScreenNav
import com.kmcoding.cartoons.view.screens.list.CartoonsListViewModel
import com.kmcoding.cartoons.view.screens.list.CartoonsScreen
import com.kmcoding.cartoons.view.theme.CartoonsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CartoonsTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = CartoonsListScreenNav) {
          composable<CartoonsListScreenNav> {
            val cartoonsListViewModel: CartoonsListViewModel = hiltViewModel()
            val cartoons by cartoonsListViewModel.cartoons.collectAsState()
            CartoonsScreen(cartoons = cartoons, navigateToDetails = { cartoon ->
              navController.navigate(CartoonDetailScreenNav2)
            }, modifier = Modifier.fillMaxSize())
          }

          composable<CartoonDetailScreenNav2> { backStackEntry ->
            //val cartoon = backStackEntry.toRoute<CartoonDetailScreenNav>().cartoon
            //CartoonDetailScreen(cartoon = cartoon)
          }
        }
      }
    }
  }
}