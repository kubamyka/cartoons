package com.kmcoding.cartoons.view.screens.home.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonDetailsPane(cartoon: Cartoon, navigateBack: () -> Unit) {
  val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
  val showNavigationIcon = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED

  Scaffold(topBar = {
    TopAppBar(colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer,
      titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer), title = {
      Text(text = cartoon.title, maxLines = 1, overflow = TextOverflow.Ellipsis,
        modifier = Modifier.testTag(stringResource(id = R.string.tag_top_bar)))
    }, navigationIcon = {
      if (showNavigationIcon) {
        IconButton(onClick = navigateBack) {
          Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back))
        }
      }
    })
  }) { innerPadding ->
    Column(modifier = Modifier
      .padding(innerPadding)
      .fillMaxSize()
      .verticalScroll(rememberScrollState())) {
      AsyncImage(model = cartoon.coverUrl, contentDescription = null,
        error = painterResource(id = R.drawable.ic_no_photo), contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth())
      CartoonDetailsTexts(cartoon = cartoon)
    }
  }
}

@Composable
fun CartoonDetailsTexts(cartoon: Cartoon, modifier: Modifier = Modifier) {
  Column(modifier = modifier.padding(16.dp)) {
    LabelledText(label = R.string.creation_year, value = cartoon.year.toString())
    LabelledText(label = R.string.episodes, value = cartoon.episodes.toString())
    LabelledText(label = R.string.episode_duration, value = cartoon.episodeDuration.toString())

    if (cartoon.creators.isNotEmpty()) {
      LabelledTextForListOfStrings(singleResource = R.string.creator,
        pluralResource = R.string.creators, list = cartoon.creators)
    }
    if (cartoon.genres.isNotEmpty()) {
      LabelledTextForListOfStrings(singleResource = R.string.genre,
        pluralResource = R.string.genres, list = cartoon.genres)
    }
  }
}

@Composable
private fun LabelledText(@StringRes
label: Int, value: String) {
  Text(text = stringResource(id = label), style = MaterialTheme.typography.bodySmall,
    fontWeight = FontWeight.Bold)
  Text(text = value, style = MaterialTheme.typography.bodyLarge,
    modifier = Modifier.padding(bottom = 12.dp))
}

@Composable
private fun LabelledTextForListOfStrings(@StringRes
singleResource: Int,
  @StringRes
  pluralResource: Int, list: List<String>) {
  LabelledText(label = if (list.size == 1) singleResource else pluralResource,
    value = if (list.size == 1) list[0] else list.joinToString(", "))
}

@Preview(showBackground = true)
@Composable
fun CartoonDetailPanePreview() {
  CartoonDetailsPane(cartoon = fakeCartoons[0], navigateBack = {})
}
