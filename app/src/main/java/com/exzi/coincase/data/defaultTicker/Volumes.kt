package com.exzi.coincase.data.defaultTicker

import com.google.gson.annotations.SerializedName

data class Volumes(

    @SerializedName("d" ) var d : Double? = null,
    @SerializedName("m" ) var m : Double? = null,
    @SerializedName("w" ) var w : Double? = null

)
