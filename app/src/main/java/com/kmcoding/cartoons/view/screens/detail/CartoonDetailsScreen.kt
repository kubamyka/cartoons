package com.kmcoding.cartoons.view.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.domain.util.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class CartoonDetailScreenNav(val cartoon: Cartoon? = null) {
  companion object {
    val typeMap = mapOf(typeOf<Cartoon>() to serializableType<Cartoon>())

    fun from(savedStateHandle: SavedStateHandle) =
      savedStateHandle.toRoute<CartoonDetailScreenNav>()
  }
}

@Serializable
object CartoonDetailScreenNav2

@Composable
fun CartoonDetailScreen(/*cartoon: Cartoon*/){
  Column {
    Text(text = "test")
  }
}