package com.kmcoding.cartoons.view.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonDetailScreen(cartoon: Cartoon, modifier: Modifier = Modifier, navigateBack: () -> Unit = {}) {
  Scaffold(topBar = {
    TopAppBar(
      colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary),
      title = {
        Text(text = cartoon.title)
      },
      navigationIcon = {
        IconButton(onClick = navigateBack) {
          Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back))
        }
      })
  }, modifier = modifier) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      AsyncImage(model = cartoon.coverUrl, contentDescription = null,
        modifier = Modifier.fillMaxWidth())
      Text(text = cartoon.title, style = MaterialTheme.typography.titleLarge)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CartoonDetailScreenPreview() {
  CartoonDetailScreen(cartoon = fakeCartoons[0])
}
