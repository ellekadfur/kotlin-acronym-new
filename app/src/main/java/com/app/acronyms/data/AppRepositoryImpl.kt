package com.app.acronyms.data

import com.app.acronyms.common.Constant
import com.app.acronyms.common.ResultOf
import com.app.acronyms.data.network.model.APIResponse
import com.app.acronyms.data.network.APIInterface
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val mockApiInterface: APIInterface
) : AppRepository {
    override suspend fun fetchMeanings(acronym: String?): ResultOf<List<APIResponse>?> {
        return try {
            val response = mockApiInterface.fetch(acronym)
            if (response.isSuccessful) {
                ResultOf.Success(response.body())
            } else {
                ResultOf.Failure(
                    response.message() ?: Constant.ErrorConstant.GENERIC_ERROR_MESSAGE,
                    Throwable(response.message())
                )

            }
        } catch (e: Exception) {
            ResultOf.Failure(e.message, e)
        }
    }
}