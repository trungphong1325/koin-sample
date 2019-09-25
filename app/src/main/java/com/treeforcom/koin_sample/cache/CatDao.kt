package com.treeforcom.koin_sample.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.treeforcom.koin_sample.model.Cat

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(cats: List<Cat>)
    @Query("SELECT * FROM Cat")
    suspend fun getAll(): List<Cat>
}