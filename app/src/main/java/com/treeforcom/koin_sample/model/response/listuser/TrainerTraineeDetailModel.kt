package com.treeforcom.koin_sample.model.response.listuser

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainerTraineeDetailModel(
    @PrimaryKey
    val id: Int,
    val avatar: String,
    val name: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val distance: Double,
    val rating: Double,
    val hour: Double
)