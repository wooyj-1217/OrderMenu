package com.wooyj.ordermenu.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.wooyj.ordermenu.data.local.room.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDAOFlow {
    @Query("SELECT * FROM breed WHERE name = :name")
    fun getBreedByName(name: String): Flow<BreedEntity?>

    @Query("SELECT * FROM breed")
    fun getBreedList(): Flow<List<String>>
}
