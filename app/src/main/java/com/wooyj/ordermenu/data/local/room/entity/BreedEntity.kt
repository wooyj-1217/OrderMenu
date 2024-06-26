package com.wooyj.ordermenu.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class BreedEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val breedId: String,
    val name: String,
    val subBreedName: String,
)
