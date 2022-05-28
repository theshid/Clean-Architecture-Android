package com.example.ecomkt.app.di

import android.content.Context
import androidx.room.Room
import com.example.ecomkt.data_local.db.AppDatabase
import com.example.ecomkt.data_local.db.dao.CommunityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCommunityDao(db:AppDatabase): CommunityDao = db.communityDao()
}