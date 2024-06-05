package com.exzi.coincase.data.defaultTicker

import com.google.gson.annotations.SerializedName

data class Second(

    @SerializedName("id"        ) var id       : Double?    = null,
    @SerializedName("decimal"   ) var decimal  : Double?    = null,
    @SerializedName("iso3"      ) var iso3     : String? = null,
    @SerializedName("name"      ) var name     : String? = null,
    @SerializedName("rate_usd"  ) var rateUsd  : Double?    = null,
    @SerializedName("rate_usdt" ) var rateUsdt : Double?    = null,
    @SerializedName("rate_btc"  ) var rateBtc  : Double?    = null,
    @SerializedName("rate_eth"  ) var rateEth  : Double?    = null

)
