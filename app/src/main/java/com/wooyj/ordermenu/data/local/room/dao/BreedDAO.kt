package com.wooyj.ordermenu.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wooyj.ordermenu.data.local.room.entity.BreedEntity

@Dao
interface BreedDAO {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: BreedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<BreedEntity>)

    @Query("SELECT * FROM breed WHERE name = :name")
    suspend fun getBreedByName(name: String): BreedEntity?

    @Query("SELECT * FROM breed")
    suspend fun getBreedList(): List<String>

    @Update
    suspend fun update(data: BreedEntity)

    @Update
    suspend fun update(data: List<BreedEntity>)

    @Delete
    suspend fun delete(data: BreedEntity)

    @Delete
    suspend fun delete(data: List<BreedEntity>)

    @Query("DELETE FROM breed WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("DELETE FROM breed")
    suspend fun deleteAll()
}
