package com.treeforcom.koin_sample.cache.listuser

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.treeforcom.koin_sample.model.response.listuser.TrainerTraineeDetailModel

@Dao
interface ListTrainerTraineeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListUser(listUser: List<TrainerTraineeDetailModel>)

    @Query("SELECT * FROM TrainerTraineeDetailModel")
    suspend fun getAllListUser(): List<TrainerTraineeDetailModel>
}