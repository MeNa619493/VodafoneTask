package com.example.vodafonetask.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vodafonetask.models.Airline
import com.example.vodafonetask.util.Constants

@Database(entities = [Airline::class], version = 1, exportSchema = false)
abstract class AirlinesDatabase : RoomDatabase() {

    abstract val airlineDao: AirlinesDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: AirlinesDatabase

        fun getInstance(context: Context): AirlinesDatabase {
            synchronized(AirlinesDatabase::class.java) {
                if (!Companion::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AirlinesDatabase::class.java,
                        Constants.DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}