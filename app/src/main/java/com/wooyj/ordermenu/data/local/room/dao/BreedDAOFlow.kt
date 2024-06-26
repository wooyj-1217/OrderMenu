package com.wooyj.ordermenu.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDAOFlow {
    @Query("SELECT name FROM breed")
    fun getBreedList(): Flow<List<String>>

    @Query("SELECT subBreedName FROM breed WHERE name = :breedName")
    fun getSubBreedList(breedName: String): Flow<List<String>>
}
