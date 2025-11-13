package com.example.nolimits.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nolimits.data.local.dao.AppUserDao
import com.example.nolimits.data.local.model.AppUser

@Database(
    entities = [AppUser::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAOs
    abstract fun appUserDao(): AppUserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nolimits_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
