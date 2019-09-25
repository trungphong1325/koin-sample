package com.treeforcom.koin_sample.api.bookingmanage

import com.treeforcom.koin_sample.model.response.bookingmanager.BookingManagerResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface BookingManageService {
    @GET("trainee/booking")
    fun getListBookingManageAsync(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("is_upcoming") isUpcoming: Int,
        @Query("today") today: String
    ) : Deferred<BookingManagerResponse>
}