package com.kmcoding.cartoons.view.screens.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon

@Composable
fun CartoonDetailsPane(cartoon: Cartoon) {
  Column(modifier = Modifier
    .fillMaxSize()
    .verticalScroll(rememberScrollState())) {
    AsyncImage(model = cartoon.coverUrl, contentDescription = null,
      error = painterResource(id = R.drawable.ic_no_photo), contentScale = ContentScale.FillWidth,
      modifier = Modifier.fillMaxWidth())
    CartoonDetailsTexts(cartoon = cartoon)
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
  CartoonDetailsPane(cartoon = fakeCartoons[0])
}
