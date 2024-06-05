package com.exzi.coincase.remote

import com.exzi.coincase.Base
import com.exzi.coincase.data.bookList.BookListModel
import com.exzi.coincase.data.defaultTicker.Data
import com.exzi.coincase.utils.Endpoints
import retrofit2.Response
import retrofit2.http.*


interface CoinCaseService {

    @GET(Endpoints.COIN_END_POINT)
    suspend fun getBooks() :Response<Base<List<Data>>>

    /*@GET(TASK_DETAIL_END_POINT)
    suspend fun getTaskDetail(
        @Query("id") id: Int
    ): Response<Base<MissionDetailResponse>>


    @POST(ADD_PLAINT_END_POINT)
    suspend fun postPlaint(@Body plaintRequest: PlaintRequest): Response<Base<String>>

    @PUT(TASK_UPDATE_STATUS_END_POINT)
    suspend fun putUpdateTaskStatus(@Body taskUpdateStatusRequest: TaskUpdateStatusRequest): Response<Base<String>>
*/
}