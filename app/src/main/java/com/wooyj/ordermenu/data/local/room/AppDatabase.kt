package com.wooyj.ordermenu.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wooyj.ordermenu.data.DATABASE_VERSION
import com.wooyj.ordermenu.data.local.room.dao.BreedDAO
import com.wooyj.ordermenu.data.local.room.dao.BreedDAOFlow
import com.wooyj.ordermenu.data.local.room.dao.FavoriteDogDAO
import com.wooyj.ordermenu.data.local.room.entity.BreedEntity
import com.wooyj.ordermenu.data.local.room.entity.FavoriteDogEntity

@Database(version = DATABASE_VERSION, entities = [BreedEntity::class, FavoriteDogEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDAO

    abstract fun breedDaoFlow(): BreedDAOFlow

    abstract fun favoriteDogDao(): FavoriteDogDAO
}
