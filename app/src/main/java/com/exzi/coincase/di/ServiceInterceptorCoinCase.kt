package com.exzi.coincase.di

import com.exzi.coincase.repository.PreferencesRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceInterceptorCoinCase
@Inject constructor(
    private var preferencesRepository: PreferencesRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
        Timber.e("XXX CoinCase")
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

}