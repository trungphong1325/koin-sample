package com.treeforcom.koin_sample.cache

import androidx.room.RoomDatabase
import com.treeforcom.koin_sample.cache.listuser.ListTrainerTraineeDao
import com.treeforcom.koin_sample.model.Cat
import com.treeforcom.koin_sample.model.response.listuser.TrainerTraineeDetailModel

@androidx.room.Database(entities = [
    Cat::class, TrainerTraineeDetailModel::class
], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun listTrainerTraineeDao(): ListTrainerTraineeDao
}