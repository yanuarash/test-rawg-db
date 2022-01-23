package com.example.rawgdb.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark (
    @PrimaryKey(autoGenerate = true)var id: Int,
    var name: String,
    var rating: Float,
    var released: String,
    var background: String
)