package com.kmcoding.cartoons.data.repository

import com.kmcoding.cartoons.data.source.FakeDataSource.fakeCartoons
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import kotlinx.coroutines.flow.flow

class FakeCartoonRepositoryImpl : CartoonRepository {

  override suspend fun getCartoons() = flow {
    emit(fakeCartoons)
  }
}