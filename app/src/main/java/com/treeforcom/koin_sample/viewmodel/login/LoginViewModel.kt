package com.treeforcom.koin_sample.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.treeforcom.koin_sample.SingleLiveEvent
import com.treeforcom.koin_sample.model.request.login.LoginEmaiParam
import com.treeforcom.koin_sample.model.response.login.UserResponse
import com.treeforcom.koin_sample.repository.login.LoginRepository
import com.treeforcom.koin_sample.usecase.UseCaseResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()
    val userModel = MutableLiveData<UserResponse>()
    val showError = SingleLiveEvent<String>()

    fun authenticate(param: LoginEmaiParam) {
        showLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) { loginRepository.authenticate(param) }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> userModel.value = result.data
                is UseCaseResult.Error -> showError.value = result.errorMessage
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
