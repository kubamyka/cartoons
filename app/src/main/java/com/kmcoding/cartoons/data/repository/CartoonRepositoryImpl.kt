package com.kmcoding.cartoons.data.repository

import com.kmcoding.cartoons.data.remote.CartoonApi
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartoonRepositoryImpl
    @Inject
    constructor(private val api: CartoonApi) : CartoonRepository {
        override suspend fun getCartoons() =
            flow {
                emit(api.getCartoons())
            }
    }
