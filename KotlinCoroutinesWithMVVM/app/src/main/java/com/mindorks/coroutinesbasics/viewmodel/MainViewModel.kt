package com.mindorks.coroutinesbasics.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindorks.coroutinesbasics.model.APIResponse
import com.mindorks.coroutinesbasics.repository.MainRepository
import com.mindorks.coroutinesbasics.util.Result
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()
    private val responseLiveData = MutableLiveData<Result<APIResponse>>()

    fun getAPIResponse() {
        viewModelScope.launch {
            responseLiveData.postValue(Result.loading(null))
            val output = mainRepository.getAPIData()
            if (output.isSuccessful) {
                responseLiveData.postValue(Result.success(output.body()))
            } else {
                responseLiveData.postValue(Result.error("Something Went Wrong", null))

            }
        }
    }

    fun getResponse(): LiveData<Result<APIResponse>> {
        return responseLiveData
    }
}