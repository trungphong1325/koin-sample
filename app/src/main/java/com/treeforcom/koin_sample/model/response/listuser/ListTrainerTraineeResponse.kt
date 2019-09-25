package com.treeforcom.koin_sample.model.response.listuser

data class ListTrainerTraineeResponse(val success: Boolean, val message: String,
                                      val code: Int, val data: TrainerTraineeDataModel)