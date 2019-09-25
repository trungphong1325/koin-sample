package com.treeforcom.koin_sample.model.response.login

data class UserModel(
    val id: Int,
    val avatar: String,
    val name: String,
    val nickname: String,
    val email: String,
    val is_trainer: Int,
    val login_token: String
)