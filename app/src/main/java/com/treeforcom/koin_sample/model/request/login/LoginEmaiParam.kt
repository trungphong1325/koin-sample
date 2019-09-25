package com.treeforcom.koin_sample.model.request.login

data class LoginEmaiParam(
    val email: String,
    val password: String,
    val check_login: Int,
    val device_type: String
)