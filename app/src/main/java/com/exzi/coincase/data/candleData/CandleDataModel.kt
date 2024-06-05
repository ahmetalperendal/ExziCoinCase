package com.exzi.coincase.data.candleData

import com.google.gson.annotations.SerializedName

data class CandleDataModel(

    @SerializedName("low"      ) var low     : Double?    = null,
    @SerializedName("high"     ) var high    : Double?    = null,
    @SerializedName("volume"   ) var volume  : Double?    = null,
    @SerializedName("time"     ) var time    : Double?    = null,
    @SerializedName("open"     ) var open    : Double?    = null,
    @SerializedName("close"    ) var close   : Double?    = null,
    @SerializedName("pair_id"  ) var pairId  : Double?    = null,
    @SerializedName("low_f"    ) var lowF    : String? = null,
    @SerializedName("high_f"   ) var highF   : String? = null,
    @SerializedName("open_f"   ) var openF   : String? = null,
    @SerializedName("close_f"  ) var closeF  : String? = null,
    @SerializedName("volume_f" ) var volumeF : String? = null

)