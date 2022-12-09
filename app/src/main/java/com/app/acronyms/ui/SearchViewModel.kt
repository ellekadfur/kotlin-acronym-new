package com.app.acronyms.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.acronyms.common.Constant
import com.app.acronyms.common.ResultOf
import com.app.acronyms.usecase.GetMeanings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getMeanings: GetMeanings) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun isLoading(): LiveData<Boolean> = _isLoading
    private val _noResult: MutableLiveData<Boolean> = MutableLiveData(false)
    fun isNoResult(): LiveData<Boolean> = _noResult
    val inputText = MutableLiveData("")
    private val _result: MutableLiveData<List<String>?> = MutableLiveData(null)
    fun onResult(): LiveData<List<String>?> = _result
    private val _error: MutableLiveData<String> = MutableLiveData("")
    fun error(): LiveData<String> = _error


    suspend fun search(string: String) {
        resetStates()
        setLoading(true)
        when (val result = getMeanings.execute(string)) {
            is ResultOf.Failure -> {
                _error.postValue(result.message ?: Constant.ErrorConstant.GENERIC_ERROR_MESSAGE)
            }
            is ResultOf.Success -> {
                _noResult.postValue(result.value.isEmpty())
                _result.postValue(result.value)
            }
        }
        setLoading(false)
    }

    private fun setLoading(setLoading: Boolean) {
        _isLoading.postValue(setLoading)
    }

    private fun resetStates() {
        setLoading(false)
        _error.postValue("")
        _noResult.postValue(false)
    }


}