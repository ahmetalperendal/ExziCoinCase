package com.exzi.coincase.di

import android.os.Environment
import com.exzi.coincase.remote.CoinCaseService
import com.exzi.coincase.utils.Constants.CACHE_MAX_SIZE
import com.exzi.coincase.utils.Endpoints.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideConvertorFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @CoinCaseOkhttpClient
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        serviceInterceptorCoinCase: ServiceInterceptorCoinCase
    ): OkHttpClient {
        val cache = Cache(Environment.getDownloadCacheDirectory(), CACHE_MAX_SIZE.toLong())
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(serviceInterceptorCoinCase)
            .build()
    }

    @Provides
    @RetrofitCoinCase
    fun provideRetrofit(
        @CoinCaseOkhttpClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Provides
    @ServiceCoinCase
    fun provideService(
        @RetrofitCoinCase retrofit: Retrofit
    ): CoinCaseService = retrofit.create(CoinCaseService::class.java)


}