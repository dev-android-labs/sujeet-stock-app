package com.upstox.android.sujeet.stockapp.di

import android.app.Application
import androidx.room.Room
import com.upstox.android.sujeet.stockapp.data.local.HoldingsDatabase
import com.upstox.android.sujeet.stockapp.data.local.dao.HoldingsDao
import com.upstox.android.sujeet.stockapp.data.remote.HoldingsApi
import com.upstox.android.sujeet.stockapp.data.repository.HoldingsRepositoryImpl
import com.upstox.android.sujeet.stockapp.domain.repository.HoldingsRepository
import com.upstox.android.sujeet.stockapp.utils.API_BASE_URL
import com.upstox.android.sujeet.stockapp.utils.DATABASE_NAME_holding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideApi(retrofit: Retrofit): HoldingsApi =
        retrofit.create(HoldingsApi::class.java)

    @Provides
    fun provideDatabase(app: Application): HoldingsDatabase =
        Room.databaseBuilder(app, HoldingsDatabase::class.java, DATABASE_NAME_holding).build()

    @Provides
    fun provideDao(db: HoldingsDatabase): HoldingsDao = db.holdingsDao()

    @Provides
    fun provideRepository(api: HoldingsApi, dao: HoldingsDao): HoldingsRepository =
        HoldingsRepositoryImpl(api, dao)
}
