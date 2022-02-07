package com.example.loginlesson26.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrackDao {

    @Insert
    fun saveTrack(tracks: List<TopTrackEntity>)

    @Query("SELECT * FROM tracks")
    fun getTrack(): List<TopTrackEntity>

}