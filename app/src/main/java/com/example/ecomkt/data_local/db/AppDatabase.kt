package com.example.ecomkt.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecomkt.data_local.db.dao.CommunityDao
import com.example.ecomkt.data_local.db.entity.DbCommunityInformation

@Database(entities = [DbCommunityInformation::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME: String = "app_db"
    }
     abstract fun communityDao(): CommunityDao
}