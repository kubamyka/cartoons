package com.kmcoding.cartoons.di

import com.kmcoding.cartoons.data.remote.CartoonApi
import com.kmcoding.cartoons.data.repository.CartoonRepositoryImpl
import com.kmcoding.cartoons.domain.repository.CartoonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.sampleapis.com").client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    @Singleton
    fun provideCartoonsApi(retrofit: Retrofit): CartoonApi {
        return retrofit.create(CartoonApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartoonsRepository(api: CartoonApi): CartoonRepository {
        return CartoonRepositoryImpl(api)
    }
}
