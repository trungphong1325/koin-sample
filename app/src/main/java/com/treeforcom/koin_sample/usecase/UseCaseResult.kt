package com.treeforcom.koin_sample.usecase

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val errorMessage: String = "") : UseCaseResult<Nothing>()
}