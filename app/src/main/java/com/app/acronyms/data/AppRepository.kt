package com.app.acronyms.data

import com.app.acronyms.common.ResultOf
import com.app.acronyms.data.network.model.APIResponse

interface AppRepository {
    suspend fun fetchMeanings(acronym: String?): ResultOf<List<APIResponse>?>
}