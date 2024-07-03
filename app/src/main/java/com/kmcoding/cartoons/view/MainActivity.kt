package com.kmcoding.cartoons.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
        val cartoonsListViewModel: CartoonsListViewModel = hiltViewModel()
        val cartoons by cartoonsListViewModel.cartoons.collectAsState()
        CartoonsScreen(cartoons = cartoons, modifier = Modifier.fillMaxSize())
      }
    }
  }
}