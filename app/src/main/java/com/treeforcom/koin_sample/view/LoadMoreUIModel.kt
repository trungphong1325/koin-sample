package com.treeforcom.koin_sample.view

data class LoadMoreUIModel(
    var totalItem: Int? = 1,
    var currentPage: Int? = 1,
    var isLoadMore: Boolean? = false,
    var isLoadingIndicator: Boolean? = false
)