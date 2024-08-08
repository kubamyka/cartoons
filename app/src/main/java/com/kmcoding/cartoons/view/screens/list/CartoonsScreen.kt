package com.kmcoding.cartoons.view.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.domain.model.UiText
import com.kmcoding.cartoons.view.screens.list.components.CartoonsList
import com.kmcoding.cartoons.view.screens.list.components.SearchTopAppBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.Serializable

@Serializable
object CartoonsListScreenNav

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartoonsScreen(modifier: Modifier = Modifier,
  viewModel: CartoonsListViewModel = hiltViewModel<CartoonsListViewModel>(),
  navController: NavController = rememberNavController()) {
  val cartoons by viewModel.cartoons.collectAsStateWithLifecycle()
  val isSearchActive by viewModel.isSearchActive.collectAsStateWithLifecycle()
  val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
  val query by viewModel.searchQuery.collectAsStateWithLifecycle()
  val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = viewModel::fetchCartoons)
  val snackBarHostState = remember { SnackbarHostState() }
  val context = LocalContext.current

  LaunchedEffect(key1 = snackBarHostState) {
    viewModel.snackBarChannel.collect { message->
      snackBarHostState.showSnackbar(message = message.asString(context))
    }
  }

  Scaffold(snackbarHost = {
    SnackbarHost(hostState = snackBarHostState)
  }, topBar = {
    SearchTopAppBar(query = query, onQueryChange = { viewModel.updateQuery(it) }, isSearchActive = isSearchActive,
      toggleSearchActive = { viewModel.toggleSearchActive() })
  }, modifier = modifier) { innerPadding ->
    Box(modifier = Modifier
      .fillMaxSize()
      .padding(innerPadding)
      .pullRefresh(pullRefreshState)) {
      if (cartoons.isEmpty()) {
        Box(modifier = Modifier
          .fillMaxSize()
          .pullRefresh(pullRefreshState)
          .verticalScroll(rememberScrollState()), contentAlignment = Alignment.Center) {
          Text(text = stringResource(id = R.string.empty_list))
        }
      } else {
        CartoonsList(cartoons = cartoons, navigateToDetails = { cartoon ->
          navController.navigate(route = cartoon)
        }, modifier = Modifier.testTag(stringResource(id = R.string.tag_cartoons_list)))
      }

      PullRefreshIndicator(
        refreshing = isLoading,
        state = pullRefreshState,
        modifier = Modifier.align(Alignment.TopCenter),
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CartoonsScreenPreview() {
  CartoonsScreen()
}