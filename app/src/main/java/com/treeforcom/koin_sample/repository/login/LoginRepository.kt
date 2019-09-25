package com.treeforcom.koin_sample.repository.login

import com.treeforcom.koin_sample.model.request.login.LoginEmaiParam
import com.treeforcom.koin_sample.model.response.login.UserResponse
import com.treeforcom.koin_sample.usecase.UseCaseResult

interface LoginRepository {
    suspend fun authenticate(param: LoginEmaiParam): UseCaseResult<UserResponse>
}