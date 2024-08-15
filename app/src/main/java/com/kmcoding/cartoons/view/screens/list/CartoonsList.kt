package com.kmcoding.cartoons.view.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kmcoding.cartoons.domain.model.Cartoon

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartoonsListPane(
  navigateToDetails: (Cartoon) -> Unit,
  onQueryChange: (String) -> Unit,
  toggleSearchActive: () -> Unit,
  pullRefreshState: PullRefreshState,
  modifier: Modifier = Modifier,
  cartoons: List<Cartoon> = listOf(),
  isSearchActive: Boolean = false,
  isLoading: Boolean = false,
  query: String = ""
) {
  Box(modifier = Modifier
    .fillMaxSize()
    .padding(16.dp)
    .pullRefresh(pullRefreshState)) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
      item {
        CartoonsSearchBar(query = query, onQueryChange = { onQueryChange(it) },
          isSearchActive = isSearchActive, toggleSearchActive = { toggleSearchActive() })
      }
      items(key = { cartoon ->
        cartoon.id
      }, items = cartoons) {
        CartoonItem(cartoon = it, navigateToDetails = navigateToDetails)
      }
    }
    PullRefreshIndicator(
      refreshing = isLoading,
      state = pullRefreshState,
      modifier = Modifier.align(Alignment.TopCenter),
    )
  }
}

