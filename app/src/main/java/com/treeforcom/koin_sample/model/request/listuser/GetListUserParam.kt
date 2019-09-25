package com.treeforcom.koin_sample.model.request.listuser

data class GetListUserParam(
    var page: Int,
    var limit: Int,
    var lat: Double?,
    val long: Double?,
    var youpert_category_id: Int,
    var nickname: String?,
    var age_from: Int,
    var age_to: Int)

