package com.treeforcom.koin_sample.api.login

import com.treeforcom.koin_sample.model.response.login.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("login-via-email")
    fun authenticateAsync(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("check_login") checkLogin: Int,
        @Field("device_type") device_type: String
    ) : Deferred<UserResponse>
}