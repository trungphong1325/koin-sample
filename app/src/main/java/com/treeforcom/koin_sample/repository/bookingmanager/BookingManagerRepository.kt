package com.treeforcom.koin_sample.repository.bookingmanager

import com.treeforcom.koin_sample.model.request.bookingmanager.BookingManagerParam
import com.treeforcom.koin_sample.model.response.bookingmanager.BookingManagerResponse
import com.treeforcom.koin_sample.usecase.UseCaseResult

interface BookingManagerRepository{
    suspend fun getBookingManager(param: BookingManagerParam) : UseCaseResult<BookingManagerResponse>
}