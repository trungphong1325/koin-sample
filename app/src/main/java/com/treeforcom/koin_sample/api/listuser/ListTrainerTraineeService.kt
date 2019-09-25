package com.treeforcom.koin_sample.api.listuser

import com.treeforcom.koin_sample.model.response.listuser.ListTrainerTraineeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ListTrainerTraineeService {
    @GET("trainee/trainer?")
    fun getListTrainerOrTraineeAsync(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("lat") lat: Double?,
        @Query("lng") lng: Double?,
        @Query("youpert_category_id") youpertCategoryId: Int,
        @Query("nickname") nickname: String?,
        @Query("age_from") ageFrom: Int,
        @Query("age_to") ageTo: Int
    ): Deferred<ListTrainerTraineeResponse>
}