package com.kmcoding.cartoons.domain.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.kmcoding.cartoons.domain.model.Cartoon
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T : Parcelable> parcelableType(
  isNullableAllowed: Boolean = false,
  json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
  override fun get(bundle: Bundle, key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      bundle.getParcelable(key, T::class.java)
    } else {
      @Suppress("DEPRECATION") bundle.getParcelable(key)
    }

  override fun parseValue(value: String): T = json.decodeFromString(value)

  override fun serializeAsValue(value: T): String = json.encodeToString(value)

  override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}

inline fun <reified T : Any> serializableType(
  isNullableAllowed: Boolean = false,
  json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
  override fun get(bundle: Bundle, key: String) =
    bundle.getString(key)?.let<String, T>(json::decodeFromString)

  override fun parseValue(value: String): T = json.decodeFromString(value)

  override fun serializeAsValue(value: T): String = json.encodeToString(value)

  override fun put(bundle: Bundle, key: String, value: T) {
    bundle.putString(key, json.encodeToString(value))
  }
}

val CartoonType = object : NavType<Cartoon>(isNullableAllowed = false) {
  override fun get(bundle: Bundle, key: String): Cartoon? {
    return bundle.getString(key)?.let { Json.decodeFromString<Cartoon>(it) }
  }

  override fun parseValue(value: String) = Json.decodeFromString<Cartoon>(value)

  override fun serializeAsValue(value: Cartoon): String = Json.encodeToString(value)

  override fun put(bundle: Bundle, key: String, value: Cartoon) {
    bundle.putString(key, Json.encodeToString(value))
  }
}