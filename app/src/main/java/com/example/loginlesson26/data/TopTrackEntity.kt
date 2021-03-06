package com.example.loginlesson26.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "tracks"
)
data class TopTrackEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "artist")
    val artist: String,
    @ColumnInfo(name = "image")
    val image: String,
)