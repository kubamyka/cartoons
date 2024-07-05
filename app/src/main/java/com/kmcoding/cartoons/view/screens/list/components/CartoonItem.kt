package com.kmcoding.cartoons.view.screens.list.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kmcoding.cartoons.R
import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.model.Cartoon

@Composable
fun CartoonItem(modifier: Modifier = Modifier, cartoon: Cartoon,
  navigateToDetails: (Cartoon) -> Unit) {
  Card(elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(align = Alignment.Top)
      .padding(top = 16.dp)) {
    Row(modifier = Modifier
      .fillMaxWidth()
      .clickable { navigateToDetails(cartoon) }
      .background(color = MaterialTheme.colorScheme.onPrimary),
      verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
      CartoonCover(coverUrl = cartoon.coverUrl)
      CartoonContent(cartoon = cartoon)
    }
  }
}

@Composable
fun CartoonCover(coverUrl: String) {
  Log.e("TAG", coverUrl)
  Card(shape = RoundedCornerShape(16.dp), border = BorderStroke(width = 1.dp, color = Color.Black),
    modifier = Modifier.padding(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary)){
      AsyncImage(model = coverUrl, error = painterResource(id = R.drawable.ic_no_photo),
        contentDescription = "", modifier = Modifier.size(64.dp), contentScale = ContentScale.Crop)
    }
  }
}

@Composable
fun CartoonContent(cartoon: Cartoon) {
  Column(modifier = Modifier
    .padding(12.dp),
    horizontalAlignment = Alignment.Start) {
    Text(text = cartoon.title, style = MaterialTheme.typography.titleLarge)
    Text(text = stringResource(id = R.string.episodes, cartoon.episodes),
      style = MaterialTheme.typography.bodySmall)
    Text(text = stringResource(id = R.string.year, cartoon.year),
      style = MaterialTheme.typography.bodySmall)
  }
}

@Preview(showBackground = true)
@Composable
fun CartoonItemPreview() {
  CartoonItem(cartoon = fakeCartoons[0], navigateToDetails = {})
}