package com.treeforcom.koin_sample.repository.bookingmanager

import com.treeforcom.koin_sample.api.bookingmanage.BookingManageService
import com.treeforcom.koin_sample.model.request.bookingmanager.BookingManagerParam
import com.treeforcom.koin_sample.model.response.bookingmanager.BookingManagerResponse
import com.treeforcom.koin_sample.usecase.UseCaseResult

class BookingManagerRepositoryImpl(private val service: BookingManageService) :
    BookingManagerRepository {
    override suspend fun getBookingManager(param: BookingManagerParam): UseCaseResult<BookingManagerResponse> {
        return try {
            val result = service.getListBookingManageAsync(
                param.page,
                param.limit,
                param.isUpcoming,
                param.today
            ).await()
            when {
                result.success -> UseCaseResult.Success(result)
                else -> UseCaseResult.Error(result.message)
            }
        } catch (ex: Throwable) {
            UseCaseResult.Error(ex.message ?: "")
        }
    }

}