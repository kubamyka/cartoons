package com.kmcoding.cartoons.di

import com.kmcoding.cartoons.data.remote.CartoonApi
import com.kmcoding.cartoons.data.repository.CartoonRepositoryImpl
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideCartoonsApi(): CartoonApi {
    return Retrofit.Builder()
      .baseUrl("https://api.sampleapis.com")
      .build()
      .create(CartoonApi::class.java)
  }

  @Provides
  @Singleton
  fun provideCartoonsRepository(api: CartoonApi) : CartoonRepository {
    return CartoonRepositoryImpl(api)
  }
}