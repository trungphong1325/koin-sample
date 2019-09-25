package com.treeforcom.koin_sample.repository

import com.treeforcom.koin_sample.model.Cat
import com.treeforcom.koin_sample.usecase.UseCaseResult

interface CatRepository {
    suspend fun getCatList(): UseCaseResult<List<Cat>>
}
