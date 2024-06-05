package com.exzi.coincase

import com.google.gson.annotations.SerializedName

data class Base<T>(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("data") var data: T,
    @SerializedName("is_login") var isLogin: Boolean? = null,
)
