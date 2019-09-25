package com.treeforcom.koin_sample.model.response.bookingmanager

import com.treeforcom.koin_sample.model.response.login.UserModel

data class BookingManagerModel(
    val id: Int,
    val booking_code: String,
    val begin_at: String,
    val end_at: String,
    val trainer: UserModel,
    val trainee: UserModel
)