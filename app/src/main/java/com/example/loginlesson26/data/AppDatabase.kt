package com.example.loginlesson26.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1, entities = [TopTrackEntity::class], exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTopTrackEntity(): TrackDao

    companion object {
        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase").build()
        }
    }

}
