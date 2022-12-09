package com.app.acronyms.usecase

import com.app.acronyms.common.ResultOf
import com.app.acronyms.data.AppRepository
import javax.inject.Inject

class GetMeanings @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(acronym: String?): ResultOf<List<String>> {
        if (acronym.isNullOrBlank()) return emptyResult()
        return when (val meanings = appRepository.fetchMeanings(acronym)) {
            is ResultOf.Failure -> {
                return meanings
            }
            is ResultOf.Success -> {
                val result = mutableListOf<String>()
                meanings.value?.forEach { apiResponse ->
                    apiResponse.lfs?.forEach { lf ->
                        lf.lf?.let {
                            result.add(it)
                        }
                    }
                }
                return result.takeIf { it.isNotEmpty() }?.let { ResultOf.Success(it) }
                    ?: emptyResult()
            }
        }
    }

    private fun emptyResult() = ResultOf.Success<List<String>>(emptyList())
}