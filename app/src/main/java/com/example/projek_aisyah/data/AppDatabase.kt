package com.example.projek_aisyah.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projek_aisyah.data.dao.ObatDao
import com.example.projek_aisyah.data.entity.ObatEntity

@Database(
    entities = [ObatEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun obatDao(): ObatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mediremind_db"
                ).build().also { INSTANCE = it }
            }
    }
}