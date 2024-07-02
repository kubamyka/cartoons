package com.kmcoding.cartoons.domain.repository

import com.kmcoding.cartoons.domain.model.Cartoon
import kotlinx.coroutines.flow.Flow

interface CartoonRepository {
  suspend fun getCartoons(): Flow<Cartoon>
}