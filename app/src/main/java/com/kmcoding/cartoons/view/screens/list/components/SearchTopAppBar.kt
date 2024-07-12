package com.kmcoding.cartoons.view.screens.list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmcoding.cartoons.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(query: String, onQueryChange: (String) -> Unit, isSearchActive: Boolean = false,
  toggleSearchActive: () -> Unit) {
  Surface(shadowElevation = 4.dp) {
    TopAppBar(colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer,
      titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer), title = {
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        if (isSearchActive) {
          TextField(value = query, placeholder = {
            Text(text = stringResource(id = R.string.enter_phrase_here), fontSize = 18.sp,
              color = MaterialTheme.colorScheme.onSecondary)
          }, textStyle = TextStyle.Default.copy(fontSize = 18.sp), onValueChange = onQueryChange,
            maxLines = 1,
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent, errorContainerColor = Color.Transparent,
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent,
              disabledIndicatorColor = Color.Transparent), leadingIcon = {
              Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(R.string.search),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }, modifier = Modifier.weight(1f))
        } else {
          Text(text = stringResource(id = R.string.app_name))
          Spacer(modifier = Modifier.weight(1f))
        }

        IconButton(
          onClick = toggleSearchActive,
          modifier = Modifier.padding(end = 12.dp),
        ) {
          Icon(
            imageVector = if (isSearchActive) Icons.Rounded.Close else Icons.Rounded.Search,
            contentDescription = if (isSearchActive) stringResource(
              R.string.close) else stringResource(R.string.search),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    })
  }
}