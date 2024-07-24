package com.kmcoding.cartoons.view.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.view.screens.list.components.CartoonsList
import com.kmcoding.cartoons.view.screens.list.components.SearchTopAppBar
import kotlinx.serialization.Serializable

@Serializable
object CartoonsListScreenNav

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartoonsScreen(
  query: String, onQueryChange: (String) -> Unit, toggleSearchActive: () -> Unit,
  navigateToDetails: (Cartoon) -> Unit, modifier: Modifier = Modifier,
  cartoons: List<Cartoon> = listOf(), isSearchActive: Boolean = false, isLoading: Boolean = false,
  pullRefreshState: PullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {})
) {
  Scaffold(topBar = {
    SearchTopAppBar(query = query, onQueryChange = onQueryChange, isSearchActive = isSearchActive,
      toggleSearchActive = toggleSearchActive)
  }, modifier = modifier) { innerPadding ->
    Box(modifier = Modifier
      .fillMaxSize()
      .padding(innerPadding)
      .pullRefresh(pullRefreshState)) {
      if (cartoons.isEmpty()) {
        Box(modifier = Modifier
          .fillMaxSize()
          .pullRefresh(pullRefreshState)
          .verticalScroll(rememberScrollState()),
          contentAlignment = Alignment.Center) {
          Text(text = stringResource(id = R.string.empty_list))
        }
      } else {
        CartoonsList(cartoons = cartoons, navigateToDetails = navigateToDetails)
      }

      PullRefreshIndicator(
        refreshing = isLoading,
        state = pullRefreshState,
        modifier = Modifier.align(Alignment.TopCenter),
      )
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun CartoonsScreenPreview() {
  CartoonsScreen(query = "", cartoons = fakeCartoons, onQueryChange = {}, toggleSearchActive = {},
    navigateToDetails = {})
}