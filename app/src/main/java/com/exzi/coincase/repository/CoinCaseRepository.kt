package com.exzi.coincase.repository

import com.exzi.coincase.di.ServiceCoinCase
import com.exzi.coincase.remote.CoinCaseService
import javax.inject.Inject


class CoinCaseRepository
@Inject constructor(
    @ServiceCoinCase private val coinCase: CoinCaseService
) {

    suspend fun getSpot() = coinCase.getBooks()

}




