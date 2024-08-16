package com.kmcoding.cartoons.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.view.screens.home.HomeViewModel
import com.kmcoding.cartoons.view.screens.home.detail.CartoonDetailsPane
import com.kmcoding.cartoons.view.screens.home.list.CartoonsListPane

private val WINDOW_WIDTH_LARGE = 1200.dp

@Composable
fun CartoonsApp() {
  CartoonsNavigationWrapperUI {
    CartoonsAppContent()
  }
}

@Composable
private fun CartoonsNavigationWrapperUI(content: @Composable () -> Unit = {}) {
  val selectedDestination: CartoonsDestination by remember {
    mutableStateOf(CartoonsDestination.Cartoons)
  }

  val windowSize = with(LocalDensity.current) {
    currentWindowSize().toSize().toDpSize()
  }
  val navLayoutType = if (windowSize.width >= WINDOW_WIDTH_LARGE) {
    NavigationSuiteType.NavigationDrawer
  } else {
    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())
  }

  NavigationSuiteScaffold(navigationSuiteItems = {
    CartoonsDestination.entries.forEach {
      item(
        label = { Text(stringResource(it.labelRes)) },
        icon = { Icon(it.icon, stringResource(it.labelRes)) },
        selected = it == selectedDestination,
        onClick = { /*TODO update selection*/ },
      )
    }
  }, layoutType = navLayoutType, contentColor = Color.Red) {
    content()
  }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterialApi::class)
@Composable
fun CartoonsAppContent(viewModel: HomeViewModel = hiltViewModel()) {
  val selectedCartoon by viewModel.selectedCartoon.collectAsStateWithLifecycle()
  val cartoons by viewModel.cartoons.collectAsStateWithLifecycle()
  val navigator = rememberListDetailPaneScaffoldNavigator<Long>()
  val isSearchActive by viewModel.isSearchActive.collectAsStateWithLifecycle()
  val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
  val query by viewModel.searchQuery.collectAsStateWithLifecycle()
  val pullRefreshState =
    rememberPullRefreshState(refreshing = isLoading, onRefresh = viewModel::fetchCartoons)
  val snackBarHostState = remember { SnackbarHostState() }
  val context = LocalContext.current

  BackHandler(navigator.canNavigateBack()) {
    navigator.navigateBack()
  }

  LaunchedEffect(key1 = snackBarHostState) {
    viewModel.snackBarChannel.collect { message ->
      snackBarHostState.showSnackbar(message = message.asString(context))
    }
  }

  Scaffold(snackbarHost = {
    SnackbarHost(hostState = snackBarHostState)
  }) { innerPadding ->
    ListDetailPaneScaffold(directive = navigator.scaffoldDirective, value = navigator.scaffoldValue,
      listPane = {
        AnimatedPane {
          CartoonsListPane(cartoons = cartoons, navigateToDetails = { cartoon ->
            viewModel.updateSelectedCartoon(cartoon)
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, cartoon.id.toLong())
          }, onQueryChange = viewModel::updateQuery,
            toggleSearchActive = viewModel::toggleSearchActive, isSearchActive = isSearchActive,
            query = query, isLoading = isLoading, pullRefreshState = pullRefreshState)
        }
      }, detailPane = {
        AnimatedPane {
          if (selectedCartoon != null) {
            CartoonDetailsPane(cartoon = selectedCartoon!!,
              navigateBack = { navigator.navigateBack() })
          }
        }
      }, modifier = Modifier.padding(innerPadding))
  }
}
