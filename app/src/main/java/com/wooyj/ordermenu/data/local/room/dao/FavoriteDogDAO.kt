package com.wooyj.ordermenu.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wooyj.ordermenu.data.local.room.entity.FavoriteDogEntity

@Dao
interface FavoriteDogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: FavoriteDogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<FavoriteDogEntity>)

    @Update
    suspend fun update(data: FavoriteDogEntity)

    @Update
    suspend fun update(data: List<FavoriteDogEntity>)

    @Delete
    suspend fun delete(data: FavoriteDogEntity)

    @Query("DELETE FROM favorite_dog")
    suspend fun deleteAll()
}
