package com.kmcoding.cartoons.data.repository

import com.kmcoding.cartoons.data.remote.CartoonApi
import com.kmcoding.cartoons.domain.model.Cartoon
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class CartoonRepositoryImpl @Inject constructor(private val api: CartoonApi) : CartoonRepository {

  override suspend fun getCartoons(): Flow<Cartoon> {
    return api.getCartoons().asFlow()
  }
}