package com.example.rawgdb.roomdb

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkRepository(context: Context) {
    var db: BookmarkDao = AppDatabase.getInstance(context)?.bookmarkDao()!!

    suspend fun getAllBookmark(): List<Bookmark>{
        return db.getAllBookmark()
    }

    suspend fun getAllBookmarkId(): List<Int>{
        return db.getAllBookmarkId()
    }

    suspend fun getBookmark(id: Int): Bookmark{
        return db.getBookmark(id)
    }

    suspend fun insertBookmark(bookmark: Bookmark){
        db.insertBookmark(bookmark)
    }

    suspend fun updateBookmark(bookmark: Bookmark){
        db.updateBookmark(bookmark)
    }

    suspend fun deleteBookmark(id: Int){
        db.deleteBookmark(id)
    }
}