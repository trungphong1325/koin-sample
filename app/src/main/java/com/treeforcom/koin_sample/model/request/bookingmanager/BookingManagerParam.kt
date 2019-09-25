package com.treeforcom.koin_sample.model.request.bookingmanager

data class BookingManagerParam(
    var page: Int,
    val limit: Int,
    val isUpcoming: Int,
    val today: String
)
