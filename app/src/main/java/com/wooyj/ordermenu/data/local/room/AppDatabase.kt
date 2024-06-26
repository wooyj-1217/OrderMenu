package com.wooyj.ordermenu.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wooyj.ordermenu.data.local.room.dao.BreedDAO
import com.wooyj.ordermenu.data.local.room.dao.BreedDAOFlow
import com.wooyj.ordermenu.data.local.room.dao.FavoriteDogDAO
import com.wooyj.ordermenu.data.local.room.dao.FavoriteDogDAOFlow
import com.wooyj.ordermenu.data.local.room.entity.BreedEntity
import com.wooyj.ordermenu.data.local.room.entity.FavoriteDogEntity

private const val DATABASE_VERSION = 1

// TODO("실무에서 room으로 캐시데이터처럼 사용하는 경우가 있나요?")
@Database(version = DATABASE_VERSION, entities = [BreedEntity::class, FavoriteDogEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDAO

    abstract fun breedDaoFlow(): BreedDAOFlow

    abstract fun favoriteDogDao(): FavoriteDogDAO

    abstract fun favoriteDogDaoFlow(): FavoriteDogDAOFlow
}
