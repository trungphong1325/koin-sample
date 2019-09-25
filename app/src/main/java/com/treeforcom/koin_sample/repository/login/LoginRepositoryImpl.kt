package com.treeforcom.koin_sample.repository.login

import com.treeforcom.koin_sample.api.login.LoginService
import com.treeforcom.koin_sample.model.request.login.LoginEmaiParam
import com.treeforcom.koin_sample.model.response.login.UserResponse
import com.treeforcom.koin_sample.usecase.UseCaseResult

class LoginRepositoryImpl(private val service: LoginService) : LoginRepository {
    override suspend fun authenticate(param: LoginEmaiParam): UseCaseResult<UserResponse> {
        return try {
            val result = service.authenticateAsync(
                param.email,
                param.password,
                param.check_login,
                param.device_type
            ).await()
            when {
                result.success -> UseCaseResult.Success(result)
                else -> UseCaseResult.Error(result.message)
            }
        } catch (ex: Throwable) {
            UseCaseResult.Error(ex.message?:"")
        }
    }
}
