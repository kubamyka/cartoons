package com.kmcoding.cartoons.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Cartoon(
  val id: Int = 0,
  val title: String = "",
  val year: Int = 0,
  val episodes: Int = 0,
  @SerializedName("runtime_in_minutes") val episodeDuration: Int = 0,
  @SerializedName("image") val coverUrl: String = "",
  @SerializedName("creator") val creators: List<String> = listOf(),
  @SerializedName("genre") val genres: List<String> = listOf()
) : Parcelable