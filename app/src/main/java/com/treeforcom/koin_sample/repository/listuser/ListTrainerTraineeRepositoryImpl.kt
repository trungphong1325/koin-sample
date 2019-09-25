package com.treeforcom.koin_sample.repository.listuser

import com.treeforcom.koin_sample.api.listuser.ListTrainerTraineeService
import com.treeforcom.koin_sample.cache.listuser.ListTrainerTraineeDao
import com.treeforcom.koin_sample.model.request.listuser.GetListUserParam
import com.treeforcom.koin_sample.model.response.listuser.ListTrainerTraineeResponse
import com.treeforcom.koin_sample.usecase.UseCaseResult

class ListTrainerTraineeRepositoryImpl(private val service: ListTrainerTraineeService, private val listUserDao: ListTrainerTraineeDao) :
    ListTrainerTraineeRepository {
    override suspend fun getListTrainerOrTrainee(param: GetListUserParam): UseCaseResult<ListTrainerTraineeResponse> {
        return try {
            val result = service.getListTrainerOrTraineeAsync(
                param.page,
                param.limit,
                param.lat,
                param.long,
                param.youpert_category_id,
                param.nickname,
                param.age_from, param.age_to
            ).await()
            listUserDao.addListUser(result.data.data)
            when {
                result.success -> UseCaseResult.Success(result)
                else -> UseCaseResult.Error(result.message)
            }
        } catch (ex: Throwable) {
            UseCaseResult.Error(ex.message ?: "")
        }
    }

}