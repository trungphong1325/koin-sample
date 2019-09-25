package com.treeforcom.koin_sample.repository

import com.treeforcom.koin_sample.api.CatApi
import com.treeforcom.koin_sample.cache.CatDao
import com.treeforcom.koin_sample.model.Cat
import com.treeforcom.koin_sample.usecase.UseCaseResult

class CatRepositoryImpl(private val catApi: CatApi, private val catDao : CatDao) : CatRepository {

    override suspend fun getCatList(): UseCaseResult<List<Cat>> {
        /*
         We try to return a list of cats from the API
         Await the result from web service and then return it, catching any error from API
         */
        return try {
            val result = catApi.getCats(limit = NUMBER_OF_CATS).await()
            catDao.add(result)
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Success(catDao.getAll())
        }
    }
    companion object{
        const val NUMBER_OF_CATS = 15
    }
}