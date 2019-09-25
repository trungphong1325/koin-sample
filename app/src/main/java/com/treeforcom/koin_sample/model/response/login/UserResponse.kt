package com.treeforcom.koin_sample.model.response.login

data class UserResponse(val success: Boolean,
                        val message: String,
                        val code: Int,
                        val data: UserModel)