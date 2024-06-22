package com.wooyj.ordermenu.data.local.di

import android.content.Context
import androidx.room.Room
import com.wooyj.ordermenu.data.DATABASE_NAME
import com.wooyj.ordermenu.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideBreedDao(database: AppDatabase) = database.breedDao()

    @Provides
    @Singleton
    fun provideBreedDaoFlow(database: AppDatabase) = database.breedDaoFlow()

    @Provides
    @Singleton
    fun provideFavoriteDogDao(database: AppDatabase) = database.favoriteDogDao()
}
