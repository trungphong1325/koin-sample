package com.treeforcom.koin_sample.model.response.bookingmanager

data class BookingManagerResponse(
    val success: Boolean,
    val message: String,
    val code: Int,
    val data: BookingManagerData
)