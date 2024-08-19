package com.kmcoding.cartoons.view

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.kmcoding.cartoons.R

enum class CartoonsDestination(
    @StringRes
    val labelRes: Int,
    val icon: ImageVector,
) {
    Cartoons(R.string.menu_home, Icons.Default.Home),
    Favourites(R.string.menu_favourites, Icons.Default.Favorite),
    Settings(R.string.menu_settings, Icons.Default.Settings),
}
