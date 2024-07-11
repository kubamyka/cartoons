package com.kmcoding.cartoons.view.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.view.screens.list.components.CartoonsList
import com.kmcoding.cartoons.view.screens.list.components.SearchTopAppBar
import kotlinx.serialization.Serializable

@Serializable
object CartoonsListScreenNav

@Composable
fun CartoonsScreen(
  query: String, onQueryChange: (String) -> Unit, toggleSearchActive: () -> Unit,
  navigateToDetails: (Cartoon) -> Unit, modifier: Modifier = Modifier,
  cartoons: List<Cartoon> = listOf(), isSearchActive: Boolean = false,
) {
  Scaffold(topBar = {
    SearchTopAppBar(query = query, onQueryChange = onQueryChange, isSearchActive = isSearchActive,
      toggleSearchActive = toggleSearchActive)
  }, modifier = modifier) { innerPadding ->
    Box(modifier = Modifier.padding(innerPadding)) {
      CartoonsList(cartoons = cartoons, navigateToDetails = navigateToDetails)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CartoonsScreenPreview() {
  CartoonsScreen(query = "", cartoons = fakeCartoons, onQueryChange = {}, toggleSearchActive = {},
    navigateToDetails = {})
}