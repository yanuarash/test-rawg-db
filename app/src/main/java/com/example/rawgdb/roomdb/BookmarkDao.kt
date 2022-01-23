package com.example.rawgdb.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarkDao {

    @Query("Select * from bookmark")
    suspend fun getAllBookmark(): List<Bookmark>

    @Query("Select id from bookmark")
    suspend fun getAllBookmarkId(): List<Int>

    @Query("Select * from bookmark where id = :id")
    suspend fun getBookmark(id: Int): Bookmark

    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmark where id = :id")
    suspend fun deleteBookmark(id: Int)
}