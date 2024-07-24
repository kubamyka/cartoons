package com.kmcoding.cartoons.view.screens.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon

@Composable
fun CartoonsList(
  modifier: Modifier = Modifier,
  cartoons: List<Cartoon> = listOf(),
  navigateToDetails: (Cartoon) -> Unit
) {
  LazyColumn(modifier = modifier.fillMaxSize(),
    contentPadding = PaddingValues(all = 16.dp)){
    items(
      items = cartoons,
      key = { cartoon ->
        cartoon.id
      }
    ) {
      CartoonItem(
        cartoon = it,
        navigateToDetails = navigateToDetails
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CartoonsListPreview() {
  CartoonsList(
    cartoons = fakeCartoons,
    navigateToDetails = {}
  )
}

