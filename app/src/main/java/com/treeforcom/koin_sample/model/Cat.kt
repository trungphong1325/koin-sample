package com.treeforcom.koin_sample.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Cat(
    @PrimaryKey
    val id: String,
    @SerializedName("url")
    val imageUrl: String
)