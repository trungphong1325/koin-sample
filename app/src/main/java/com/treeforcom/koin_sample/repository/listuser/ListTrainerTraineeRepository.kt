package com.treeforcom.koin_sample.repository.listuser

import com.treeforcom.koin_sample.model.request.listuser.GetListUserParam
import com.treeforcom.koin_sample.model.response.listuser.ListTrainerTraineeResponse
import com.treeforcom.koin_sample.usecase.UseCaseResult

interface ListTrainerTraineeRepository {
    suspend fun getListTrainerOrTrainee(param: GetListUserParam) : UseCaseResult<ListTrainerTraineeResponse>
}