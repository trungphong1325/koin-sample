package com.treeforcom.koin_sample.viewmodel.bookingmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.treeforcom.koin_sample.SingleLiveEvent
import com.treeforcom.koin_sample.model.request.bookingmanager.BookingManagerParam
import com.treeforcom.koin_sample.model.response.bookingmanager.BookingManagerResponse
import com.treeforcom.koin_sample.repository.bookingmanager.BookingManagerRepository
import com.treeforcom.koin_sample.usecase.UseCaseResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BookingManagerViewModel(private val repository: BookingManagerRepository) : ViewModel(),
    CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()
    val bookingManager = MutableLiveData<BookingManagerResponse>()
    val showError = SingleLiveEvent<String>()

    fun getBookingManager(param: BookingManagerParam){
        showLoading.postValue(true)
        launch {
            val result = withContext(Dispatchers.Main){repository.getBookingManager(param)}
            showLoading.postValue(false)
            when(result){
                is UseCaseResult.Success -> bookingManager.value = result.data
                is UseCaseResult.Error -> showError.value = result.errorMessage
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}