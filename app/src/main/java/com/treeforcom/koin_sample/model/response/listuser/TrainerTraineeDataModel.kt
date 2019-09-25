package com.treeforcom.koin_sample.model.response.listuser

data class TrainerTraineeDataModel(val current_page: Int,
                                   val data: List<TrainerTraineeDetailModel>,
                                   val total: Int)