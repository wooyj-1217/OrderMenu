package com.wooyj.ordermenu.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.wooyj.ordermenu.data.local.room.entity.FavoriteDogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDogDAOFlow {
    @Query("SELECT * FROM favorite_dog ORDER BY breedId DESC")
    suspend fun getFavoriteDogList(): Flow<List<FavoriteDogEntity>>
}
