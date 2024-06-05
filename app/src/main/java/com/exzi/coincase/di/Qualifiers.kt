package com.exzi.coincase.di

import javax.inject.Qualifier



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoinCaseOkhttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServiceCoinCase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitCoinCase