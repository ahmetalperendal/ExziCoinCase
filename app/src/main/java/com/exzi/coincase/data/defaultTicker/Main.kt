package com.exzi.coincase.data.defaultTicker

import com.google.gson.annotations.SerializedName

data class Main(

    @SerializedName("id"                 ) var id                : Double?    = null,
    @SerializedName("decimal"            ) var decimal           : Double?    = null,
    @SerializedName("iso3"               ) var iso3              : String? = null,
    @SerializedName("name"               ) var name              : String? = null,
    @SerializedName("circulating_supply" ) var circulatingSupply : String? = null,
    @SerializedName("maximum_supply"     ) var maximumSupply     : String? = null,
    @SerializedName("total_supply"       ) var totalSupply       : String? = null,
    @SerializedName("cap"                ) var cap               : Double?    = null,
    @SerializedName("cap_f"              ) var capF              : String? = null,
    @SerializedName("rate_usd"           ) var rateUsd           : Double?    = null,
    @SerializedName("rate_usdt"          ) var rateUsdt          : Double?    = null,
    @SerializedName("rate_btc"           ) var rateBtc           : Double?    = null,
    @SerializedName("rate_eth"           ) var rateEth           : Double?    = null


)