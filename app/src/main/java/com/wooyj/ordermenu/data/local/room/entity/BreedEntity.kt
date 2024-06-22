package com.wooyj.ordermenu.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class BreedEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val subName: String,
)
