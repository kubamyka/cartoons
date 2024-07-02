package com.kmcoding.cartoons.domain.model

import kotlinx.serialization.SerialName

data class Cartoon(
  val id: Int = 0,
  val title: String = "",
  val year: Int = 0,
  val episodes: Int = 0,
  @SerialName("runtime_in_minutes") val episodeDuration: Int = 0,
  @SerialName("image") val coverUrl: String = "",
  @SerialName("creator") val creators: List<String> = listOf(),
  @SerialName("genre") val genres: List<String> = listOf()
)