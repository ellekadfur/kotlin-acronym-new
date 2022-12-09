package com.app.acronyms.data.network

import com.app.acronyms.common.Constant
import com.app.acronyms.data.network.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET(Constant.NetworkConstant.EndPoint.ACRONYM_SEARCH)
    suspend fun fetch(@Query("sf") string: String?): Response<List<APIResponse>>
}