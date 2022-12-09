package com.app.acronyms

import com.app.acronyms.common.ResultOf
import com.app.acronyms.data.network.model.APIResponse
import com.google.gson.reflect.TypeToken
import io.mockk.every
import io.mockk.mockk
import okhttp3.Response
import okhttp3.ResponseBody

object Mocks {

    const val MOCK_QUERY = "FBI"
    const val MOCK_API_ERROR_MESSAGE = "Failed to get result"


    fun getMockAcronymAPIResults(): List<APIResponse>? {
        var mockList: List<APIResponse>?
        val type = object : TypeToken<List<APIResponse>>() {}.type
        mockList = read("meanings", type)
        return mockList

    }

    fun getMockApiInterfaceResponse(): retrofit2.Response<List<APIResponse>> {
        var mockList: List<APIResponse>?
        val type = object : TypeToken<List<APIResponse>>() {}.type
        mockList = read("meanings", type)
        val mockRetrofitResponse = mockk<retrofit2.Response<List<APIResponse>>>()
        every { mockRetrofitResponse.isSuccessful } returns true
        every { mockRetrofitResponse.body() } returns mockList
        return mockRetrofitResponse
    }

    fun getMockApiInterfaceErrorResponse(): retrofit2.Response<List<APIResponse>> {
        val mockRetrofitResponse = mockk<retrofit2.Response<List<APIResponse>>>()
        every { mockRetrofitResponse.isSuccessful } returns false
        every { mockRetrofitResponse.message() } returns MOCK_API_ERROR_MESSAGE
        return mockRetrofitResponse
    }

    fun getMockMissingAcronymAPIResults(): List<APIResponse>? {
        var mockList: List<APIResponse>?
        val type = object : TypeToken<List<APIResponse>>() {}.type
        mockList = read("invalid_response", type)
        return mockList

    }

    fun expectedGetMeaningsUseCaseResults(): ResultOf.Success<MutableList<String>> {
        val list = mutableListOf<String>()
        list.add("Federal Bureau of Investigation")
        list.add("Frontal Behavioral Inventory")
        list.add("fresh blood imaging")
        list.add("foreign body infections")
        return ResultOf.Success(list)
    }
}