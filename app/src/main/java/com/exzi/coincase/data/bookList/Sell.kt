package com.exzi.coincase.data.bookList

import com.google.gson.annotations.SerializedName

data class Sell(

    @SerializedName("volume"   ) var volume  : Long?    = null,
    @SerializedName("count"    ) var count   : Long?    = null,
    @SerializedName("rate"     ) var rate    : Long?    = null,
    @SerializedName("price"    ) var price   : Long?    = null,
    @SerializedName("rate_f"   ) var rateF   : String? = null,
    @SerializedName("volume_f" ) var volumeF : String? = null
)
