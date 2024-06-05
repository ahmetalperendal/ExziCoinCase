package com.exzi.coincase.data.bookList

import com.google.gson.annotations.SerializedName

data class BookListModel(


    @SerializedName("buy"  ) var buy  : ArrayList<Buy>  = arrayListOf(),
    @SerializedName("sell" ) var sell : ArrayList<Sell> = arrayListOf()
)
