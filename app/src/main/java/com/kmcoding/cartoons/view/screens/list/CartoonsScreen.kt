@file:OptIn(ExperimentalMaterial3Api::class)

package com.kmcoding.cartoons.view.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.view.screens.list.components.CartoonsList
import kotlinx.serialization.Serializable

@Serializable
object CartoonsListScreenNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonsScreen(modifier: Modifier = Modifier, cartoons: List<Cartoon> = listOf(),
  navigateToDetails: (Cartoon) -> Unit) {
  Scaffold(topBar = {
    CenterAlignedTopAppBar(
      colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary), title = {
        Text(stringResource(id = R.string.app_name))
      })
  }, modifier = modifier) { innerPadding ->
    Box(modifier = Modifier.padding(innerPadding)) {
      CartoonsList(cartoons = cartoons, navigateToDetails = navigateToDetails)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CartoonsScreenPreview() {
  CartoonsScreen(cartoons = fakeCartoons, navigateToDetails = {})
}