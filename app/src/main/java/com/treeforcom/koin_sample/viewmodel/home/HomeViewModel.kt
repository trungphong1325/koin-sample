package com.treeforcom.koin_sample.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.treeforcom.koin_sample.SingleLiveEvent
import com.treeforcom.koin_sample.model.request.listuser.GetListUserParam
import com.treeforcom.koin_sample.model.response.listuser.ListTrainerTraineeResponse
import com.treeforcom.koin_sample.repository.listuser.ListTrainerTraineeRepository
import com.treeforcom.koin_sample.usecase.UseCaseResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: ListTrainerTraineeRepository) : ViewModel(),
    CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val trainerTraineeModels = MutableLiveData<ListTrainerTraineeResponse>()
    val showError = SingleLiveEvent<String>()

    fun getListTrainerOrTrainee(param: GetListUserParam) {
        showLoading.postValue(true)
        launch {
            val result = withContext(Dispatchers.Main) { repository.getListTrainerOrTrainee(param) }
            showLoading.postValue(false)
            when (result) {
                is UseCaseResult.Success -> trainerTraineeModels.value = result.data
                is UseCaseResult.Error -> showError.value = result.errorMessage
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}